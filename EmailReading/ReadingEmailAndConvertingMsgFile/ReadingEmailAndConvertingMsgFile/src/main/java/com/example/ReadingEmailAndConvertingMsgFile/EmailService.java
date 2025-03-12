package com.example.ReadingEmailAndConvertingMsgFile;


import ch.astorm.jotlmsg.OutlookMessage;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${email.host}")
    private String host;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    @Value("${email.store.path}")
    private String storePath;

    public void fetchAndSaveEmail(String subjectKeyword) {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");

        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                if (message.getSubject() != null && message.getSubject().contains(subjectKeyword)) {
                    saveEmailAsMsg(message);
                    break;
                }
            }
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveEmailAsMsg(Message message) {
        try {
            OutlookMessage msg = new OutlookMessage();
            msg.setSubject(message.getSubject());
            msg.setFrom(((MimeMessage) message).getFrom()[0].toString());
            if (message.isMimeType("text/plain")) {
                msg.setPlainTextBody((String) message.getContent());
            } else if (message.isMimeType("text/html")) {
                msg.setPlainTextBody((String) message.getContent());
            }

            String fileName = storePath + File.separator + message.getSubject().replaceAll("[^a-zA-Z0-9]", "_") + ".msg";
            System.out.println("Email saved at: " + fileName);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}

