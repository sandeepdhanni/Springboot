package com.example.PdfReading.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    @PostMapping("/readpdf")
    public ResponseEntity<String> readPdf1(@RequestParam("file") MultipartFile file) {
        System.out.println("content type: "+file.getContentType());
        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try {
            // Load the PDF document
            PdfReader pdfReader = new PdfReader(file.getInputStream());
            PdfDocument pdfDocument = new PdfDocument(pdfReader);

            StringBuilder pdfContent = new StringBuilder();

            // Extract text from all pages
            int numberOfPages = pdfDocument.getNumberOfPages();
            for (int page = 1; page <= numberOfPages; page++) {
                String pageContent = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(page));
                System.out.println(pageContent);
                pdfContent.append(pageContent).append("\n");
            }

            // Close the PDF document
            pdfDocument.close();

            return ResponseEntity.ok(pdfContent.toString());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while reading the PDF file: " + e.getMessage());
        }
    }
}
