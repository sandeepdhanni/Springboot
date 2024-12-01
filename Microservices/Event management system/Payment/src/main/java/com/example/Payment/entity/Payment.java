package com.example.Payment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentMethod; // Example: "CREDIT_CARD", "PAYPAL"

    @Column(nullable = false)
    private String status; // PENDING, SUCCESS, FAILED, REFUNDED

    @Column(nullable = false)
    private String transactionId; // Payment gateway transaction ID
}
