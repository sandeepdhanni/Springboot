package com.example.SchedulerService.controller;

import com.example.SchedulerService.service.MailReaderScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailReaderTestController {
    private final MailReaderScheduler scheduler;

    @GetMapping("/test-mail-read")
    public String testMailRead() {
        scheduler.readTodayUnreadMails();
        return "Triggered";
    }
}
