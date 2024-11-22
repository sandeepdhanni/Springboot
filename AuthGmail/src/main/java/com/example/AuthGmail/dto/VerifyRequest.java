package com.example.AuthGmail.dto;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private int selectedNumber;
}