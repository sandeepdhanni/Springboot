package com.example.Security.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class MfaChallenge {

    @Id
    private String mfaId;
    private String userId;
    private int correctNumber;
    @ElementCollection
    private List<Integer> options;
    private boolean verified;
    private LocalDateTime createdAt;

    // Constructors, Getters, Setters
}
