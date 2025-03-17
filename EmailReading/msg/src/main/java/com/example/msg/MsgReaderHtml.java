package com.example.msg;

import com.aspose.email.system.exceptions.IOException;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import java.io.File;
import java.io.FileInputStream;

//this is msg file which contains the html content which can read
public class MsgReaderHtml {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\ADMIN\\Downloads\\a0icp-3g0ay.msg";
        System.out.println("Starting to read MSG file: " + filePath);
        readMsgFile(filePath);
    }

    public static void readMsgFile(String filePath) {
        System.out.println("Attempting to open file: " + filePath);
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            System.out.println("File opened successfully.");
            MAPIMessage message = new MAPIMessage(fis);
            System.out.println("MAPIMessage object created.");

            String subject = message.getSubject();
            String from = message.getDisplayFrom();
            String to = message.getDisplayTo();
            String htmlBody = message.getHtmlBody();

            System.out.println("Extracted Subject: " + subject);
            System.out.println("Extracted From: " + from);
            System.out.println("Extracted To: " + to);
            System.out.println("Extracted HTML Body: " + htmlBody);
        } catch (ChunkNotFoundException e) {
            System.err.println("Error: Missing expected chunk in the MSG file.");
            e.printStackTrace();
        } catch (IOException | java.io.IOException e) {
            System.err.println("Error reading the MSG file.");
            e.printStackTrace();
        }
    }
}
