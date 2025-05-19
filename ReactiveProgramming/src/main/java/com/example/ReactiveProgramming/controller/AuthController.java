package com.example.ReactiveProgramming.controller;


import com.example.ReactiveProgramming.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Dummy validation
        if ("admin".equals(username) && "password".equals(password)) {
            String token = jwtService.generateToken(username);
            String refreshToken = jwtService.generateRefreshToken(username);

            return Mono.just(ResponseEntity.ok(Map.of(
                    "accessToken", token,
                    "refreshToken", refreshToken
            )));
        }

        return Mono.just(ResponseEntity.status(401).body(Map.of("error", "Invalid Credentials")));
    }
}

