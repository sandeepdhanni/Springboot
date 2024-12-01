package com.example.Notification.service;

import com.example.Notification.entity.Notification;
import com.example.Notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JavaMailSender mailSender;

    public Notification sendNotification(Notification notification) {
        try {
            // Send Email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getEmail());
            message.setSubject("Notification");
            message.setText(notification.getMessage());
            mailSender.send(message);

            // Update Notification Status
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED");
        }
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationHistory(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
