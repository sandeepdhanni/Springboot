package com.example.msg;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Slf4j
public class MsgReader {


    public static void main(String[] args) {

        String filePath = "C:\\Users\\ADMIN\\Downloads\\ash25-hfov0.msg";

        log.info("Starting to read MSG file: {}", filePath);
        readMsgFile(filePath);
    }

    public static void readMsgFile(String filePath) {
        log.info("Attempting to open file: {}", filePath);
        try (MAPIMessage message = new MAPIMessage(filePath)) {
            log.info("File opened successfully.");
            log.info("MAPIMessage object created.");

            String subject = message.getSubject();
            String from = message.getDisplayFrom();
            String to = message.getDisplayTo();
            String body = message.getTextBody();
            if (message.getHtmlBody().trim().isEmpty()) {
                log.info("html body: {}", message.getHtmlBody());
            }

            log.info("Extracted Subject: {}", subject);
            log.info("Extracted From: {}", from);
            log.info("Extracted To: {}", to);
            log.info("Extracted Body: {}", body);
        } catch (ChunkNotFoundException e) {
            log.info("Error: Missing expected chunk in the MSG file.");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("Error reading the MSG file.");
            e.printStackTrace();
        }
    }
}
