package com.example.Notification.controller;

import com.example.Notification.entity.Notification;
import com.example.Notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.sendNotification(notification));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Notification>> getNotificationHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationHistory(userId));
    }
}