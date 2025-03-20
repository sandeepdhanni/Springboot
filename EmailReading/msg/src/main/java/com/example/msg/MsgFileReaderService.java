//package com.example.msg;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.text.PDFTextStripper;
//import org.apache.poi.hsmf.MAPIMessage;
//import org.apache.poi.hsmf.datatypes.AttachmentChunks;
//import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
//import org.apache.poi.ss.usermodel.*;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//
//@Service
//public class MsgFileReaderService {
//
//    public static void main(String[] args) {
//        String filePath = "C:\\Users\\ADMIN\\Downloads\\aa4wa-6ubwv.msg";
//        System.out.println("Starting to read MSG file: " + filePath);
//        readMsgFile(filePath);
//    }
//
//    public static void readMsgFile(String filePath) {
//        try (MAPIMessage message = new MAPIMessage(filePath)) {
//
//            System.out.println("Subject: " + message.getSubject());
//            System.out.println("From: " + message.getDisplayFrom());
//            System.out.println("To: " + message.getDisplayTo());
//            System.out.println("Body: " + message.getTextBody());
//
//            // Process attachments
//            AttachmentChunks[] attachments = message.getAttachmentFiles();
//            if (attachments != null) {
//                for (AttachmentChunks attachment : attachments) {
//                    String filename = attachment.getAttachFileName() != null ? attachment.getAttachFileName().toString() : "unknown";
//                    System.out.println("Attachment Found: " + filename);
//
//                    byte[] content = attachment.getAttachData().getValue();
//                    if (filename.endsWith(".pdf")) {
//                        readPdfContent(content);
//                    } else if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
//                        readExcelContent(content);
//                    } else {
//                        System.out.println("Unsupported attachment type: " + filename);
//                    }
//                }
//            } else {
//                System.out.println("No attachments found.");
//            }
//
//        } catch (ChunkNotFoundException e) {
//            System.err.println("Chunk not found in MSG file.");
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.err.println("Error processing the MSG file.");
//            e.printStackTrace();
//        }
//    }
//
//    private static void readPdfContent(byte[] content) {
//        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(content))) {
//            PDFTextStripper pdfStripper = new PDFTextStripper();
//            String text = pdfStripper.getText(document);
//            System.out.println("PDF Content: " + text);
//        } catch (IOException e) {
//            System.err.println("Error reading PDF attachment.");
//            e.printStackTrace();
//        }
//    }
//
//    private static void readExcelContent(byte[] content) {
//        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                for (Cell cell : row) {
//                    System.out.print(cell.toString() + "\t");
//                }
//                System.out.println();
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading Excel attachment.");
//            e.printStackTrace();
//        }
//    }
//}


























package com.example.msg;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;

@Service
public class MsgFileReaderService {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\ADMIN\\Downloads\\a3woe-w9vkm.msg";
        System.out.println("Starting to read MSG file: " + filePath);
        readMsgFile(filePath);
    }

    public static void readMsgFile(String filePath) {
        try (MAPIMessage message = new MAPIMessage(filePath)) {

            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getDisplayFrom());
            System.out.println("To: " + message.getDisplayTo());
            System.out.println("Body: " + message.getTextBody());

            // Process attachments
            AttachmentChunks[] attachments = message.getAttachmentFiles();
            if (attachments != null && attachments.length > 0) {
                for (AttachmentChunks attachment : attachments) {
                    String filename = attachment.getAttachFileName() != null ? attachment.getAttachFileName().toString() : "unknown";
                    String longFilename = attachment.getAttachLongFileName() != null ? attachment.getAttachLongFileName().toString() : "N/A";

                    System.out.println("Attachment Found: " + filename);
                    System.out.println("Long File Name: " + longFilename);

                    if (attachment.getAttachData() != null) {
                        byte[] content = attachment.getAttachData().getValue();

                        if (filename.endsWith(".pdf")) {
                            readPdfContent(content);
                        } else if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
                            readExcelContent(content);
                        } else {
                            System.out.println("Unsupported attachment type or no data found: " + filename);
                        }
                    } else {
                        System.out.println("No data found for attachment: " + filename);
                    }
                }
            } else {
                System.out.println("No attachments found.");
            }

        } catch (ChunkNotFoundException e) {
            System.err.println("Chunk not found in MSG file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error processing the MSG file.");
            e.printStackTrace();
        }
    }

    private static void readPdfContent(byte[] content) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(content))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println("PDF Content: " + text);
        } catch (IOException e) {
            System.err.println("Error reading PDF attachment.");
            e.printStackTrace();
        }
    }

    private static void readExcelContent(byte[] content) {
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(content))) {
            for (Sheet sheet : workbook) {
                System.out.println("Reading Sheet: " + sheet.getSheetName());
                for (Row row : sheet) {
                    boolean isEmptyRow = true;
                    for (Cell cell : row) {
                        String cellValue = "";
                        if (cell == null || cell.getCellType() == CellType.BLANK) {
                            cellValue = "NULL";
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                // Format date to 'dd/MM/yyyy'
                                cellValue = new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                        } else if (cell.getCellType() == CellType.STRING) {
                            cellValue = cell.getStringCellValue();
                        } else {
                            cellValue = "NULL";
                        }

                        if (!"NULL".equals(cellValue)) {
                            isEmptyRow = false;
                        }

                        System.out.print(cellValue + "\t");
                    }
                    // Skip empty rows
                    if (isEmptyRow) {
                        break;
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel attachment.");
            e.printStackTrace();
        }
    }



}
