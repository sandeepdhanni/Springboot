package com.example.msg;

import com.auxilii.msgparser.MsgParser;
import com.auxilii.msgparser.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class MsgToEmlConvertor {
    public static void convertMsgToEml(String msgFilePath, String outputEmlPath) throws Exception {
        // Step 1: Parse the .msg file
        MsgParser parser = new MsgParser();
        Message msg = parser.parseMsg(new FileInputStream(msgFilePath));

        // Step 2: Create a JavaMail session
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        // Step 3: Create a MimeMessage
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject(msg.getSubject());

        // Set From address
        mimeMessage.setFrom(new InternetAddress(msg.getFromEmail(), msg.getFromName()));

        // Set To addresses
        for (String to : msg.getToRecipient()) {
            mimeMessage.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        }

        // Set email body
        if (msg.getBodyHTML() != null && !msg.getBodyHTML().isEmpty()) {
            mimeMessage.setContent(msg.getBodyHTML(), "text/html");
        } else {
            mimeMessage.setText(msg.getBodyText());
        }

        // Step 4: Write to .eml file
        try (FileOutputStream fos = new FileOutputStream(outputEmlPath)) {
            mimeMessage.writeTo(fos);
        }

        System.out.println("Conversion successful! EML file saved to: " + outputEmlPath);
    }

    public static void main(String[] args) {
        String msgFilePath = "C:\\Users\\Sreenivas Bandaru\\Desktop\\New folder (2)\\SampleInput.msg";
        String outputEmlPath = "C:\\Users\\Sreenivas Bandaru\\Desktop\\New folder (2)\\SampleOutput.eml";
        try {
            convertMsgToEml(msgFilePath, outputEmlPath);
        } catch (Exception e) {
            System.err.println("Error during conversion: " + e.getMessage());
        }
    }
}
