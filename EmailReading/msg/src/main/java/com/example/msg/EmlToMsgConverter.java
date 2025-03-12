package com.example.msg;

import com.auxilii.msgparser.Message;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.converter.EmailConverter;

import java.io.*;
import java.util.Properties;

public class EmlToMsgConverter {
//    public static void convertEmlToMsg(String emlFilePath, String outputMsgPath) throws IOException {
//        File emlFile = new File(emlFilePath);
//        if (!emlFile.exists()) {
//            throw new IOException("EML file not found at: " + emlFilePath);
//        }
//
//        // Convert EML to Email object
//        Email email = EmailConverter.emlToEmail(new FileInputStream(emlFile));
//
//        // Convert Email object to MSG and save
//        byte[] msgBytes = EmailConverter.emailToEML(email).getBytes();
//        try (FileOutputStream fos = new FileOutputStream(outputMsgPath)) {
//            fos.write(msgBytes);
//        }
//
//        System.out.println("Conversion successful! MSG file saved to: " + outputMsgPath);
//    }


//    public static void convertEmlToMsg(String emlFilePath, String outputMsgPath) throws IOException {
//        File emlFile = new File(emlFilePath);
//        if (!emlFile.exists()) {
//            throw new IOException("EML file not found at: " + emlFilePath);
//        }
//
//        // Read the EML content
//        StringBuilder emlContent = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(emlFile))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                emlContent.append(line).append("\n");
//            }
//        }
//
//        // Create MSG file using POI
//        try (POIFSFileSystem fs = new POIFSFileSystem();
//             FileOutputStream fos = new FileOutputStream(outputMsgPath)) {
//            fs.createDocument(new ByteArrayInputStream(emlContent.toString().getBytes()), "__substg1.0_1000001F");
//            fs.writeFilesystem(fos);
//        }
//
//        System.out.println("Conversion successful! MSG file saved to: " + outputMsgPath);
//    }





    public static void convertEmlToMsg(String emlFilePath, String outputMsgPath) throws Exception {
        // Load the EML file using JavaMail
        Properties props = new Properties();
        Session mailSession = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(mailSession, new FileInputStream(emlFilePath));

        try (POIFSFileSystem fs = new POIFSFileSystem();
             FileOutputStream fos = new FileOutputStream(outputMsgPath)) {

            MAPIMessage msg = new MAPIMessage();

            // Set Subject
            msg.setSubject(message.getSubject());

            // Set From
            Address[] froms = message.getFrom();
            if (froms != null && froms.length > 0) {
                msg.setDisplayFrom(froms[0].toString());
            }

            // Set To
            Address[] recipients = message.getRecipients(Message.RecipientType.TO);
            if (recipients != null) {
                msg.setDisplayTo(recipients[0].toString());
            }

            // Set Body
            Object content = message.getContent();
            if (content instanceof String) {
                msg.setTextBody((String) content);
            } else if (content instanceof Multipart) {
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    if (bodyPart.getContent() instanceof String) {
                        msg.setTextBody((String) bodyPart.getContent());
                        break;
                    }
                }
            }

            // Write the MSG file
            msg.write(fs);
            fs.writeFilesystem(fos);
        }

        System.out.println("Conversion successful! MSG file saved to: " + outputMsgPath);
    }




    public static void main(String[] args) {
        String emlFilePath = "C:\\Users\\Sreenivas Bandaru\\Desktop\\New folder (2)\\DailySatus_Java_1215_28_02_2025.eml";
        String outputMsgPath = "C:\\Users\\Sreenivas Bandaru\\Desktop\\New folder (2)\\DailySatus_Java_1215_28_02_2025.msg";
        try {
            convertEmlToMsg(emlFilePath, outputMsgPath);
        } catch (IOException e) {
            System.err.println("Error during conversion: " + e.getMessage());
        }
    }
}
