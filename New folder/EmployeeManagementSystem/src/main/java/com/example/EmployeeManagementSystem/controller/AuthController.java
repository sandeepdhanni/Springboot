package com.example.EmployeeManagementSystem.controller;

import com.example.EmployeeManagementSystem.entity.SignUp;
import com.example.EmployeeManagementSystem.entity.User;
import com.example.EmployeeManagementSystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUp signUp) throws Exception {
        User user = authService.saveUser(signUp);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignUp signUp) {
        String token = authService.authenticate(signUp);
        return ResponseEntity.ok(token);
    }
}
