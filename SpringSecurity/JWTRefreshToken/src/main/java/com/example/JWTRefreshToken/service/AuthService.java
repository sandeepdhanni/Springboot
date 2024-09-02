package com.example.JWTRefreshToken.service;

import com.example.JWTRefreshToken.entity.RefreshToken;
import com.example.JWTRefreshToken.entity.User;
import com.example.JWTRefreshToken.exceptionhandling.UserExists;
import com.example.JWTRefreshToken.model.SignUp;
import com.example.JWTRefreshToken.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class AuthService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    public User saveUser(SignUp request) throws Exception {

        if (repository.existsByName(request.getName())) {
            throw new UserExists("This Username already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword())).build();
        return repository.save(user);
    }

    public Map<String, String> authenticate(SignUp request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getName(),
                        request.getPassword())
        );
        User user = repository.findByName(request.getName()).get();

        // Generate access token
        String accessToken = jwtService.genrateToken(user);

        // Generate refresh token and save to database
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getName());

        // Prepare response with both tokens
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken.getToken());

        return tokens;
    }
}