package com.example.SchedulerService.service;

import com.example.SchedulerService.entity.MailContentEntity;
import com.example.SchedulerService.repository.MailReaderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailReaderService {

    private final MailReaderRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveEmail(Message message) throws Exception {
        String body = extractTextFromMessage(message);
        String cc = extractAddresses(message.getRecipients(Message.RecipientType.CC));
        String bcc = extractAddresses(message.getRecipients(Message.RecipientType.BCC));
        String subject = message.getSubject();
        Date receivedDate = message.getReceivedDate();

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("from", extractAddresses(message.getFrom()));
        jsonMap.put("cc", cc);
        jsonMap.put("bcc", bcc);
        jsonMap.put("subject", subject);
        jsonMap.put("receivedDate", receivedDate);
        jsonMap.put("body", body);

        String jsonData = objectMapper.writeValueAsString(jsonMap);

        // 🗂️ Download attachments (if any) and get saved file paths
        String attachmentPaths = downloadAttachments(message);


        MailContentEntity entity = new MailContentEntity();
        entity.setMailCC(cc);
        entity.setMailBcc(bcc);
        entity.setSubject(subject);
        entity.setBody(body);
        entity.setJsonData(jsonData);
        entity.setStatus("PENDING");
        entity.setReceivedOn(new Timestamp(receivedDate.getTime()));
        entity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        entity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
        entity.setPath(attachmentPaths);
        repository.save(entity);
    }

    private String extractAddresses(Address[] addresses) {
        if (addresses == null || addresses.length == 0) return null;

        StringBuilder sb = new StringBuilder();
        for (Address address : addresses) {
            if (address instanceof InternetAddress internetAddress) {
                sb.append(internetAddress.getAddress()).append(";");
            } else {
                sb.append(address.toString()).append(";");
            }
        }
        return sb.toString();
    }

    private String extractTextFromMessage(Message message) throws IOException, MessagingException {
        Object content = message.getContent();
        if (content instanceof String str) {
            return str;
        } else if (content instanceof MimeMultipart mimeMultipart) {
            return getHtmlFromMimeMultipart(mimeMultipart); // Updated method name
        }
        return "";
    }

    private String getHtmlFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            var part = mimeMultipart.getBodyPart(i);
            if (part.isMimeType("text/html")) {
                return part.getContent().toString();
            } else if (part.getContent() instanceof MimeMultipart nested) {
                String html = getHtmlFromMimeMultipart(nested);
                if (!html.isEmpty()) return html;
            }
        }
        return "";
    }


    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws IOException, MessagingException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            var part = mimeMultipart.getBodyPart(i);
            if (part.isMimeType("text/plain")) {
                result.append(part.getContent());
            } else if (part.isMimeType("text/html")) {
                result.append(part.getContent()); // Or strip HTML if needed
            }  else if (part.getContent() instanceof MimeMultipart nestedMultipart) {
                result.append(getTextFromMimeMultipart(nestedMultipart));
            }
        }
        return result.toString();
    }








    //will read all the sheets in the xls file
    public void readAllSheetsWithNames(String filePath) throws IOException {
        // Open the Excel file
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new HSSFWorkbook(fis); // For .xls files
        int numberOfSheets = workbook.getNumberOfSheets(); // Get the total number of sheets

        // Iterate through all the sheets in the workbook
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);  // Get each sheet
            String sheetName = sheet.getSheetName();  // Get sheet name
            log.info("Sheet Name: {}", sheetName);

            // Iterate through the rows and columns of the sheet
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Print out the cell value depending on the cell type
                    switch (cell.getCellType()) {
                        case STRING:
                            log.info("{}\t", cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            log.info("{}\t", cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            log.info("{}\t", cell.getBooleanCellValue());
                            break;
                        default:
                            log.info("UNKNOWN\t");
                    }
                }
                log.info(""); // New line for each row
            }
            log.info("==================================="); // Separator between sheets
        }
        fis.close();
    }






    private String downloadAttachments(Part part) throws Exception {
        StringBuilder savedPaths = new StringBuilder();

        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);

                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) ||
                        (bodyPart.getFileName() != null && !bodyPart.getFileName().isEmpty())) {

                    File file = getFile("C:\\Users\\Sreenivas Bandaru\\Desktop\\New folder\\Excel_file", bodyPart);

                    savedPaths.append(file.getAbsolutePath()).append(";");
                    log.info("📎 Attachment saved: {}", file.getAbsolutePath());
                }
            }
        }

        return savedPaths.toString();
    }

    private static File getFile(String downloadDir, BodyPart bodyPart) throws MessagingException, IOException {
        String fileName = bodyPart.getFileName();
        File file = new File(downloadDir, fileName);

        // Ensure directory exists
        new File(downloadDir).mkdirs();

        try (InputStream is = bodyPart.getInputStream();
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buf = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
        }
        return file;
    }


}