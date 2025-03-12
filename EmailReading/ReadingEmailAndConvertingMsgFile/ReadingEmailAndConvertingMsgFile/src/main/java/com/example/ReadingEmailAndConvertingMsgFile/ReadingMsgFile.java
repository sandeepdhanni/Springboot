package com.example.ReadingEmailAndConvertingMsgFile;

import ch.astorm.jotlmsg.OutlookMessage;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ReadingMsgFile {

    public void readEmailFromMsgFile(String filePath) {
        try {
            File msgFile = new File(filePath);
            if (!msgFile.exists()) {
                System.out.println("File not found: " + filePath);
                return;
            }

            // Parse the .msg file
            OutlookMessage msg = new OutlookMessage(msgFile);

            // Extract details
            System.out.println("Subject: " + msg.getSubject());
            System.out.println("From: " + msg.getFrom());
            System.out.println("To: " + msg.getReplyTo());

            // Extract body content
            System.out.println("\nEmail Content:");
            System.out.println(msg.getPlainTextBody());

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading .msg file: " + e.getMessage());
        }
    }

}
