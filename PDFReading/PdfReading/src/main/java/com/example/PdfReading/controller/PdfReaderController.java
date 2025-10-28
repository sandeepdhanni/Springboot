package com.example.PdfReading.controller;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.*;
import technology.tabula.extractors.BasicExtractionAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfReaderController {

    //this is used by pdfbox dependency
    @PostMapping("/read")
    public ResponseEntity<String> readPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            if (document.isEncrypted()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Cannot process encrypted PDF files.");
            }

            // Extract text from the PDF
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfContent = pdfStripper.getText(document);
            System.out.println(pdfContent);

            return ResponseEntity.ok(pdfContent);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while reading the PDF file: " + e.getMessage());
        }
    }


    //this is used by itextcore dependency
//    @PostMapping("/readpdf")
//    public ResponseEntity<String> readPdf1(@RequestParam("file") MultipartFile file) {
//        System.out.println("content type: "+file.getContentType());
//        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
//            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
//        }
//
//        try {
//            // Load the PDF document
//            PdfReader pdfReader = new PdfReader(file.getInputStream());
//            PdfDocument pdfDocument = new PdfDocument(pdfReader);
//
//            StringBuilder pdfContent = new StringBuilder();
//
//            // Extract text from all pages
//            int numberOfPages = pdfDocument.getNumberOfPages();
//            for (int page = 1; page <= numberOfPages; page++) {
//                String pageContent = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(page));
//                System.out.println(pageContent);
//                pdfContent.append(pageContent).append("\n");
//            }
//
//            // Close the PDF document
//            pdfDocument.close();
//
//            return ResponseEntity.ok(pdfContent.toString());
//
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred while reading the PDF file: " + e.getMessage());
//        }
//    }


    @PostMapping("/readnew")
    public ResponseEntity<?> readLeftRightRegions(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            if (document.isEncrypted()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Cannot process encrypted PDF files.");
            }

            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);

            // Adjust these rectangles as per your actual PDF coordinates
            Rectangle insuredBox = new Rectangle(10, 90, 285, 500);  // Left up to central line
            Rectangle partnerBox = new Rectangle(315, 90, 265, 500); // Right, starts after central line

            stripper.addRegion("insured", insuredBox);
            stripper.addRegion("partner", partnerBox);

            PDPage page = document.getPage(0);
            stripper.extractRegions(page);

            String insuredText = stripper.getTextForRegion("insured");
            String partnerText = stripper.getTextForRegion("partner");

            Map<String, String> insuredMap = parseLinesWithKeyValue(insuredText);
            Map<String, String> partnerMap = parseLinesWithKeyValue(partnerText);

            Map<String, String> finalMap = new LinkedHashMap<>();
            finalMap.putAll(insuredMap);
            finalMap.putAll(partnerMap);

            return ResponseEntity.ok(finalMap);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading PDF: " + e.getMessage());
        }
    }



    // Helper to check if a line is likely a label
    private boolean isLabelLine(String line) {
        return line.length() < 30 && !line.matches(".*\\d.*") && !line.equals(line.toUpperCase());
    }


    private static final Set<String> KNOWN_LABELS = Set.of(
            "Name", "Address", "Mobile Number", "Policy No", // left
            "Partner Name", "Partner Code", "Partner Mobile Number", "Partner Email" // right
    );

    private Map<String, String> parseLinesWithKeyValue(String text) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] lines = text.split("\\r?\\n");

        String currentLabel = null;
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Is this line a known label?
            Optional<String> matchedLabel = KNOWN_LABELS.stream().filter(line::startsWith).findFirst();
            if (matchedLabel.isPresent()) {
                currentLabel = matchedLabel.get();
                // Get value after ':' if present, else next line
                String value = "";
                int idx = line.indexOf(':');
                if (idx != -1 && idx + 1 < line.length())
                    value = line.substring(idx + 1).trim();
                map.put(currentLabel, value);
            } else if (currentLabel != null) {
                // continuation of previous value (multi-line)
                String prev = map.get(currentLabel);
                map.put(currentLabel, prev.isEmpty() ? line : prev + " " + line);
            }
        }
        return map;
    }


}
