//package com.example.email.controller;
//
//import com.example.email.entity.User;
//import com.example.email.repository.UserRepository;
//import com.example.email.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api")
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body("Email already exists.");
//        }
//
//        // Encrypt password and set verification token
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setVerificationToken(UUID.randomUUID().toString());
//        userRepository.save(user);
//
//        // Send verification email
//        emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
//
//        return ResponseEntity.ok("Registration successful! Check your email to verify your account.");
//    }
//
//    @GetMapping("/verify")
//    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
//        Optional<User> userOptional = userRepository.findByVerificationToken(token);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setVerified(true);
//            user.setVerificationToken(null); // Clear the token after verification
//            userRepository.save(user);
//
//            return ResponseEntity.ok("Email verified successfully!");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid verification token.");
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
//        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            if (user.isVerified()) {
//                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//                    return ResponseEntity.ok("Login successful!");
//                } else {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email is not verified.");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not found.");
//        }
//    }
//}