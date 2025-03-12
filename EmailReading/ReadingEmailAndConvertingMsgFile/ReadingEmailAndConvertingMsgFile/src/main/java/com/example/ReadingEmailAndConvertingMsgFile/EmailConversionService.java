//package com.example.ReadingEmailAndConvertingMsgFile;
//
//import ch.astorm.jotlmsg.OutlookMessage;
//import jakarta.mail.*;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//
//@Service
//public class EmailConversionService {
//
//    @Value("${email.store.path}")  // Configure the save path in application.properties
//    private String storePath;
//
//    public String convertEmlToMsg(MultipartFile emlFile) {
//        if (emlFile.isEmpty()) {
//            return "Uploaded file is empty!";
//        }
//
//        try (InputStream inputStream = emlFile.getInputStream()) {
//            // Parse the .eml file using JavaMail
//            Session session = Session.getDefaultInstance(System.getProperties(), null);
//            MimeMessage mimeMessage = new MimeMessage(session, inputStream);
//
//            // Create a new Outlook .msg file
//            OutlookMessage msg = new OutlookMessage();
//            msg.setSubject(mimeMessage.getSubject());
//
//            // ✅ Check if "From" field is null before accessing it
//            Address[] fromAddresses = mimeMessage.getFrom();
//            if (fromAddresses != null && fromAddresses.length > 0) {
//                msg.setFrom(fromAddresses[0].toString());
//            } else {
//                msg.setFrom("Unknown Sender");
//            }
//
//            // Extract plain text body
//            if (mimeMessage.isMimeType("text/plain")) {
//                msg.setPlainTextBody((String) mimeMessage.getContent());
//            } else if (mimeMessage.isMimeType("text/html")) {
//                msg.setPlainTextBody(mimeMessage.getContent().toString());  // Save HTML as plain text
//            }
//
//            // Generate a valid filename
//            String msgFileName = (mimeMessage.getSubject() != null ? mimeMessage.getSubject() : "No_Subject")
//                    .replaceAll("[^a-zA-Z0-9]", "_") + ".msg";
//            String fullPath = storePath + File.separator + msgFileName;
//
//            // Save the .msg file
//            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
//                msg.writeTo(fos);  // ✅ Corrected: Use 'write()' instead of 'writeTo()'
//            }
//
//            return "Converted file saved at: " + fullPath;
//
//        } catch (MessagingException | IOException e) {
//            e.printStackTrace();
//            return "Error converting .eml to .msg: " + e.getMessage();
//        }
//    }
//}








package com.example.ReadingEmailAndConvertingMsgFile;

import ch.astorm.jotlmsg.OutlookMessage;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class EmailConversionService {

    @Value("${email.store.path}")  // Configure the save path in application.properties
    private String storePath;

    public String convertEmlToMsg(MultipartFile emlFile) {
        if (emlFile.isEmpty()) {
            return "Uploaded file is empty!";
        }

        try (InputStream inputStream = emlFile.getInputStream()) {
            // Parse the .eml file using JavaMail
            Session session = Session.getDefaultInstance(System.getProperties(), null);
            MimeMessage mimeMessage = new MimeMessage(session, inputStream);

            // Create a new Outlook .msg file
            OutlookMessage msg = new OutlookMessage();
            msg.setSubject(mimeMessage.getSubject());

            // ✅ Check if "From" field is null before accessing it
            Address[] fromAddresses = mimeMessage.getFrom();
            if (fromAddresses != null && fromAddresses.length > 0) {
                msg.setFrom(fromAddresses[0].toString());
            } else {
                msg.setFrom("Unknown Sender");
            }

            // Extract plain text body
            if (mimeMessage.isMimeType("text/plain")) {
                msg.setPlainTextBody((String) mimeMessage.getContent());
            } else if (mimeMessage.isMimeType("text/html")) {
                msg.setPlainTextBody(mimeMessage.getContent().toString());  // Save HTML as plain text
            }

            // Generate a valid filename
            String msgFileName = (mimeMessage.getSubject() != null ? mimeMessage.getSubject() : "No_Subject")
                    .replaceAll("[^a-zA-Z0-9]", "_") + ".msg";
            String fullPath = storePath + File.separator + msgFileName;

            // Save the .msg file
            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                msg.writeTo(fos);  // ✅ Corrected: Use 'write()' instead of 'writeTo()'
            }

            return "Converted file saved at: " + fullPath;

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return "Error converting .eml to .msg: " + e.getMessage();
        }
    }
}
