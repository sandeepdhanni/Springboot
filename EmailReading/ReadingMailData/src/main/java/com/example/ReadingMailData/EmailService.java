package com.example.ReadingMailData;


import jakarta.mail.*;
import jakarta.mail.search.SubjectTerm;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public String readEmailsBySubject(String subject) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imap.host", "imap.gmail.com");
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.imap.auth", "true");


            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "EMAIL_ADDRESS", "EMAIL_PASSWORD");

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            log.info("Connecting to the mail server...");
            Message[] messages = inbox.search(new SubjectTerm(subject));
            log.info("Emails found: {}", messages.length);
            // Retrieve messages
            Message[] messages1 = inbox.getMessages();
            log.info("Message found: {}", messages.length);



            for (Message message : messages) {
                Map<String, Object> mailData = processMessage(message);
                emailRepository.save(new EmailEntity(mailData));
                log.info("Email saved with subject: {}", message.getSubject());
            }

            inbox.close(false);
            store.close();

            return "Emails processed successfully";
        } catch (Exception e) {
            log.error("Error processing emails", e);
            return "Error processing emails";
        }
    }

    private Map<String, Object> processMessage(Message message) throws Exception {
        Map<String, Object> mailData = new HashMap<>();
        mailData.put("subject", message.getSubject());
        mailData.put("from", Arrays.toString(message.getFrom()));
        mailData.put("sentDate", message.getSentDate());

        if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart part = multipart.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    log.info("Processing attachment: {}", part.getFileName());
                    processAttachment(part, mailData);
                } else {
                    mailData.put("body", part.getContent().toString());
                }
            }
        } else {
            mailData.put("body", message.getContent().toString());
        }

        return mailData;
    }

    private void processAttachment(BodyPart part, Map<String, Object> mailData) throws Exception {
        String fileName = part.getFileName();
        InputStream is = part.getInputStream();

        if (fileName.endsWith(".pdf")) {
            mailData.put("pdfData", extractPdfData(is));
        } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
            mailData.put("excelData", extractExcelData(is));
        } else if (fileName.endsWith(".html")) {
            mailData.put("htmlData", new String(is.readAllBytes()));
        }
    }

    private String extractPdfData(InputStream is) throws Exception {
        try (PDDocument document = PDDocument.load(is)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            log.info("Extracted PDF content");
            return text;
        } catch (Exception e) {
            log.error("Error extracting PDF content", e);
            throw e;
        }
    }

    private String extractExcelData(InputStream is) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                sb.append(cell.getStringCellValue()).append(" ");
                                break;
                            case NUMERIC:
                                sb.append(cell.getNumericCellValue()).append(" ");
                                break;
                            case BOOLEAN:
                                sb.append(cell.getBooleanCellValue()).append(" ");
                                break;
                            default:
                                sb.append(" ");
                        }
                    }
                    sb.append("\n");
                }
            }
            log.info("Extracted Excel content");
        } catch (Exception e) {
            log.error("Error extracting Excel content", e);
            throw e;
        }
        return sb.toString();
    }
}
