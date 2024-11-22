package com.example.AuthGmail.controller;

import com.example.AuthGmail.dto.LoginRequest;
import com.example.AuthGmail.dto.VerifyRequest;
import com.example.AuthGmail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    private final Random random = new Random();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // Generate random numbers
        int[] numbers = {random.nextInt(900) + 100, random.nextInt(900) + 100, random.nextInt(900) + 100};
        int correctNumber = numbers[random.nextInt(3)];

        // Store correct number in Redis
        redisTemplate.opsForValue().set("verification:" + request.getEmail(), correctNumber, 5, TimeUnit.MINUTES);

        // Send email
        sendEmail(request.getEmail(), correctNumber);

        // Send numbers to the frontend
        return ResponseEntity.ok(Arrays.toString(numbers));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyRequest request) {
        Integer storedNumber = (Integer) redisTemplate.opsForValue().get("verification:" + request.getEmail());
        if (storedNumber != null && storedNumber.equals(request.getSelectedNumber())) {
            return ResponseEntity.ok("Verification successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Verification failed");
    }

    private void sendEmail(String toEmail, int code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Login Verification Code");
        message.setText("Your login verification code is: " + code);
        mailSender.send(message);
    }
}
