package com.example.ReactiveProgramming.controller;


import com.example.ReactiveProgramming.JWT.JwtService;
import com.example.ReactiveProgramming.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("username");
        String rawPassword = credentials.get("password");

        return customerService.findByEmail(email)
                .flatMap(customer -> {
                    if (passwordEncoder.matches(rawPassword, customer.getPassword())) {
                        String accessToken = jwtService.generateToken(email);
                        String refreshToken = jwtService.generateRefreshToken(email);

                        return Mono.just(ResponseEntity.ok(Map.of(
                                "accessToken", accessToken,
                                "refreshToken", refreshToken
                        ))).log();
                    } else {
                        return Mono.just(ResponseEntity.status(401).body(
                                Map.of("error", "Invalid credentials"))).log();
                    }
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(401).body(
                        Map.of("error", "User not found")))).log();
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<Map<String, String>>> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            jwtService.validateToken(refreshToken);
            String email = jwtService.getUsernameFromToken(refreshToken);
            String newAccessToken = jwtService.generateToken(email);
            return Mono.just(ResponseEntity.ok(Map.of("accessToken", newAccessToken))).log();
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(401).body(
                    Map.of("error", "Invalid refresh token"))).log();
        }
    }
}
