package com.example.email.controller;

import com.example.email.entity.User;
import com.example.email.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("Registration successful. Please check your email for verification.");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token) {
        String message = userService.verifyUser(token);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        User user = userService.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getIsVerified()) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(403).body("Invalid credentials or unverified account");
    }
}
