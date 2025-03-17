package com.example.msg;

import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class MsgReader {


    public static void main(String[] args) {
        String filePath = "C:\\Users\\ADMIN\\Downloads\\ash25-hfov0.msg";
        System.out.println("Starting to read MSG file: " + filePath);
        readMsgFile(filePath);
    }

    public static void readMsgFile(String filePath) {
        System.out.println("Attempting to open file: " + filePath);
        try (MAPIMessage message = new MAPIMessage(filePath)) {
            System.out.println("File opened successfully.");
            System.out.println("MAPIMessage object created.");

            String subject = message.getSubject();
            String from = message.getDisplayFrom();
            String to = message.getDisplayTo();
            String body = message.getTextBody();
            if (message.getHtmlBody().trim().isEmpty()) {
                System.out.println("html body: "+message.getHtmlBody());
            }

            System.out.println("Extracted Subject: " + subject);
            System.out.println("Extracted From: " + from);
            System.out.println("Extracted To: " + to);
            System.out.println("Extracted Body: " + body);
        } catch (ChunkNotFoundException e) {
            System.err.println("Error: Missing expected chunk in the MSG file.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading the MSG file.");
            e.printStackTrace();
        }
    }
}
