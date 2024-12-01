package com.example.EmailReading.controller;

import com.example.EmailReading.service.GmailReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GmailReaderController {

    @Autowired
    private GmailReaderService gmailReaderService;

    @GetMapping("/read-mails")
    public String readEmails() {
        gmailReaderService.readInboxEmails();
        return "Emails read successfully!";
    }

    @GetMapping("/read-mails-from")
    public String readEmailsFromSender(@RequestParam String senderEmail) {
        gmailReaderService.readEmailsFromSender(senderEmail);
        return "Email search completed!";
    }
}