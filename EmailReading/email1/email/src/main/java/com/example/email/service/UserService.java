package com.example.email.service;

import com.example.email.entity.User;
import com.example.email.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public User registerUser(User user) {
        user = userRepository.save(user);
        sendVerificationEmail(user);
        return user;
    }

    private void sendVerificationEmail(User user) {
        String url = "http://localhost:8080/api/auth/verify?token=" + user.getVerificationToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Please verify your email using the following link: " + url);
        mailSender.send(message);
    }

    public String verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));
        user.setIsVerified(true);
        userRepository.save(user);
        return "User verified successfully";
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

}