package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.JWT.JWTService;
import com.example.EmployeeManagementSystem.entity.SignUp;
import com.example.EmployeeManagementSystem.entity.User;
import com.example.EmployeeManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    public User saveUser(SignUp request) throws Exception {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new Exception("This Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        return repository.save(user);
    }

    public String authenticate(SignUp request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return jwtService.genrateToken(user);    }
}