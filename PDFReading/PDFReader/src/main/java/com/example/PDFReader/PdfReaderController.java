package com.example.PDFReader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    private Map<String, String> parseKeyValueFromText(String text) {
//        Map<String, String> map = new LinkedHashMap<>();
//        String[] lines = text.split("\\r?\\n");
//
//        for (int i = 0; i < lines.length; i++) {
//            String line = lines[i].trim();
//
//            // 1. Try splitting by colon
//            if (line.contains(":")) {
//                String[] parts = line.split(":", 2);
//                String key = parts[0].trim();
//                String value = parts.length > 1 ? parts[1].trim() : "";
//                map.put(key, value);
//            }
//
//            // 2. Else, use next line as value
//            else if (i + 1 < lines.length) {
//                String nextLine = lines[i + 1].trim();
//
//                // Basic validation: skip if both are empty
//                if (!line.isEmpty() && !nextLine.isEmpty()) {
//                    map.put(line, nextLine);
//                    i++; // skip next line
//                }
//            }
//        }
//
//        return map;
//    }




    private Map<String, String> parseKeyValueTableFromText(String text) {
        Map<String, String> map = new LinkedHashMap<>();

        String[] lines = text.split("\\r?\\n");

        for (int i = 0; i < lines.length - 1; i += 2) {
            String headerLine = lines[i].trim();
            String valueLine = lines[i + 1].trim();

            // Split headers and values by multiple spaces or tabs
            String[] keys = headerLine.split("\\s{2,}");     // 2+ spaces or tabs as separator
            String[] values = valueLine.split("\\s{2,}");

            int len = Math.min(keys.length, values.length);
            for (int j = 0; j < len; j++) {
                map.put(keys[j].trim(), values[j].trim());
            }
        }

        return map;
    }




    @PostMapping("/readnew")
    public ResponseEntity<?> readPdf1(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            if (document.isEncrypted()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Cannot process encrypted PDF files.");
            }

            PDFTextStripper pdfStripper = new PDFTextStripper();
            String rawText = pdfStripper.getText(document);

            // Log raw content
            System.out.println("Raw PDF Content:\n" + rawText);

            // Extract structured data
            Map<String, String> extractedData = parseKeyValueTableFromText(rawText);
//            Map<String, String> extractedData = parseKeyValueFromText(rawText);


            return ResponseEntity.ok(extractedData);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while reading the PDF file: " + e.getMessage());
        }
    }













    public List<Map<String, String>> extractTableFromPdf(InputStream pdfInputStream) throws IOException {
        PDDocument document = PDDocument.load(pdfInputStream);
        ObjectExtractor extractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

        List<Map<String, String>> tableData = new ArrayList<>();

        for (int pageNum = 1; pageNum <= document.getNumberOfPages(); pageNum++) {
            Page page = extractor.extract(pageNum);
            List<Table> tables = sea.extract(page);

            for (Table table : tables) {
                List<RectangularTextContainer> headers = table.getRows().get(0);
                for (int i = 1; i < table.getRowCount(); i++) {
                    List<RectangularTextContainer> row = table.getRows().get(i);
                    Map<String, String> rowData = new LinkedHashMap<>();
                    for (int j = 0; j < headers.size(); j++) {
                        String header = headers.get(j).getText().trim();
                        String value = j < row.size() ? row.get(j).getText().trim() : "";
                        rowData.put(header, value);
                    }
                    tableData.add(rowData);
                }
            }
        }
        document.close();
        return tableData;
    }

}
