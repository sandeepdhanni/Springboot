package com.example.EmailReading.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.search.SearchTerm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class GmailReaderService {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public void readInboxEmails() {
        try {
            // Configure mail properties
            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.enable", "true");

            // Create a session
            Session session = Session.getDefaultInstance(properties);

            // Connect to the store
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Retrieve messages
            Message[] messages = inbox.getMessages();

            System.out.println("Number of emails: " + messages.length);

            for (Message message : messages) {
                if (message instanceof MimeMessage) {
                    MimeMessage mimeMessage = (MimeMessage) message;
                    System.out.println("Subject: " + mimeMessage.getSubject());
                    System.out.println("From: " + mimeMessage.getFrom()[0]);
                    System.out.println("Received Date: " + mimeMessage.getReceivedDate());
                    System.out.println("Content: " + mimeMessage.getContent());
                    System.out.println("------------------------------------------------");
                }
            }

            // Close the folder and store
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readEmailsFromSender(String senderEmail) {
        try {
            // Configure mail properties
            Properties properties = new Properties();
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.enable", "true");

            // Create a session
            Session session = Session.getDefaultInstance(properties);

            // Connect to the store
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Retrieve messages
            Message[] messages = inbox.getMessages();

            System.out.println("Number of emails: " + messages.length);
            boolean emailFound = false;

            for (Message message : messages) {
                if (message instanceof MimeMessage) {
                    MimeMessage mimeMessage = (MimeMessage) message;

                    // Check the "From" address
                    Address[] fromAddresses = mimeMessage.getFrom();
                    if (fromAddresses != null && fromAddresses.length > 0) {
                        String from = ((InternetAddress) fromAddresses[0]).getAddress();
                        if (from.equalsIgnoreCase(senderEmail)) {
                            emailFound = true;
                            System.out.println("Email found from: " + senderEmail);
                            System.out.println("Subject: " + mimeMessage.getSubject());
                            System.out.println("Received Date: " + mimeMessage.getReceivedDate());
                            System.out.println("Content: " + mimeMessage.getContent());
                            System.out.println("------------------------------------------------");
                        }
                    }
                }
            }

            if (!emailFound) {
                System.out.println("No emails found from: " + senderEmail);
            }

            // Close the folder and store
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
