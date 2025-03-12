package com.example.ReadingMailData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
public class EmailReaderController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/read")
    public String readEmails(@RequestParam String subject) {
        return emailService.readEmailsBySubject(subject);
    }
}

