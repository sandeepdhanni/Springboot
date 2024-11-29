package com.example.Payment.controller;

import com.example.Payment.entity.Payment;
import com.example.Payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<Payment> initiatePayment(@RequestBody Payment paymentRequest) {
        Payment payment = paymentService.initiatePayment(paymentRequest);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<Payment> getPaymentStatus(@PathVariable Long paymentId) {
        Optional<Payment> payment = paymentService.getPaymentStatus(paymentId);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
