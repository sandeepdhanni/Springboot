package com.example.email.service;


import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class GmailReaderService {

    private static final String HOST = "imap.gmail.com";
    private static final String PORT = "993";
    private static final String PROTOCOL = "imaps";

    public void readEmails(String email, String appPassword) {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps"); // Use secure IMAP protocol
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true"); // Enable SSL
        props.put("mail.debug", "true"); // Debugging

        try {
            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "samdhanni01@gmail.com", "your_app_password"); // Use App Password
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            System.out.println("Total Messages: " + inbox.getMessageCount());
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                System.out.println("Subject: " + message.getSubject());
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        private String getAddresses(Address[] addresses) {
        if (addresses == null || addresses.length == 0) {
            return "Unknown";
        }
        StringBuilder result = new StringBuilder();
        for (Address address : addresses) {
            result.append(address.toString()).append(", ");
        }
        return result.substring(0, result.length() - 2);
    }

    private String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
            }
        }
        return result.toString();
    }
}
