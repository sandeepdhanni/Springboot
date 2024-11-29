package com.example.Payment.service;

import com.example.Payment.entity.Payment;
import com.example.Payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment initiatePayment(Payment paymentRequest) {
        // Simulate Payment Gateway Integration
        String transactionId = UUID.randomUUID().toString(); // Generate unique transaction ID

        paymentRequest.setTransactionId(transactionId);
        paymentRequest.setStatus("SUCCESS"); // Simulate successful payment
        return paymentRepository.save(paymentRequest);
    }

    public Optional<Payment> getPaymentStatus(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }
}