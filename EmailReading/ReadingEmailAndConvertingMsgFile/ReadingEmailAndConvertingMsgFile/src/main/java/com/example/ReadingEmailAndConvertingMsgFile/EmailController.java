package com.example.ReadingEmailAndConvertingMsgFile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailConversionService emailConversionService;

    @GetMapping("/fetch")
    public String fetchAndSaveEmail(@RequestParam String subjectKeyword) {
//        log.info("entererd");
        emailService.fetchAndSaveEmail(subjectKeyword);
        return "Email fetching process started for subject: " + subjectKeyword;
    }

    @PostMapping("/convert")
    public String convertEmlToMsg(@RequestParam("file") MultipartFile file) {
        return emailConversionService.convertEmlToMsg(file);
    }
}
