package com.example.JWTRefreshToken.controller;

import com.example.JWTRefreshToken.entity.RefreshToken;
import com.example.JWTRefreshToken.entity.User;
import com.example.JWTRefreshToken.model.JwtResponse;
import com.example.JWTRefreshToken.model.RefreshTokenRequest;
import com.example.JWTRefreshToken.model.SignUp;
import com.example.JWTRefreshToken.service.AuthService;
import com.example.JWTRefreshToken.service.JwtService;
import com.example.JWTRefreshToken.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthService service;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUp request) throws Exception {
        return ResponseEntity.ok(service.saveUser(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> login(@RequestBody SignUp request) {
        Map<String, String> tokens = service.authenticate(request);
        return ResponseEntity.ok(tokens);
    }


    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Test endpoint is accessible");
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken())
                    .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));

            refreshTokenService.verifyExpiration(refreshToken);

            User user = refreshToken.getUserInfo();
            if (user == null) {
                throw new RuntimeException("User not found for the provided refresh token!");
            }

            String accessToken = jwtService.genrateToken(user);

            JwtResponse response = JwtResponse.builder()
                    .accessToken(accessToken)
                    .token(refreshTokenRequest.getToken())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
