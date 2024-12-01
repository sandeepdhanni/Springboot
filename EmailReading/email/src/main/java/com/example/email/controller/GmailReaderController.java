package com.example.email.controller;

import com.example.email.service.GmailReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GmailReaderController {

    @Autowired
    private GmailReaderService gmailReaderService;

    @GetMapping("/read-emails")
    public String readEmails(@RequestParam String email, @RequestParam String appPassword) {
        gmailReaderService.readEmails(email, appPassword);
        return "Emails have been read successfully. Check console for details.";
    }
}
