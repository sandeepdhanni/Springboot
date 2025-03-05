package com.example.controller;



import com.example.entity.Payment;
import com.example.repo.PaymentRepo;
import com.example.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepo paymentRepo;

    @PostMapping("/save/{paymentId}")
    public ResponseEntity<String> savePayment(@PathVariable String paymentId) {
        try {
            // Fetch payment details from Razorpay
            Payment payment = paymentService.savePaymentDetails(paymentId);

            // Save payment details in Oracle DB
            paymentRepo.save(payment);

            return ResponseEntity.ok("Payment details saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }



    @PostMapping("/verify/{paymentId}")
    public ResponseEntity<String> verifyPaymentStatus(@PathVariable String paymentId) {
        try {
            String status = paymentService.verifyPaymentStatus(paymentId);
            return ResponseEntity.ok("Payment status: " + status);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/methods/{paymentId}")
    public ResponseEntity<?> getPaymentMethods(@PathVariable String paymentId) {
        try {
            Map<String, String> paymentMethods = paymentService.getPaymentMethods(paymentId);
            return ResponseEntity.ok(paymentMethods);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update/{paymentId}")
    public ResponseEntity<?> updatePaymentDetails(
            @PathVariable Integer paymentId,
            @RequestBody Payment updatedPayment) {
        try {
            Payment payment = paymentService.updatePaymentDetails(paymentId, updatedPayment);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }



    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable Integer paymentId) {
        try {
            // Fetch payment details using payment ID
            Payment payment = paymentService.getPaymentById(paymentId);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }



    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<?> refundPayment(@PathVariable String paymentId) {
        try {
            Payment payment = paymentService.refundPayment(paymentId);
            return ResponseEntity.ok("Payment refunded successfully with updated status: " + payment.getStatus());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }



    //this api is used to get the payment details by paymentid
    @GetMapping("/details/{paymentId}")
    public ResponseEntity<?> getPaymentDetails(@PathVariable String paymentId) {
        try {
            JSONObject paymentDetails = paymentService.getPaymentDetails(paymentId);
            return ResponseEntity.ok(paymentDetails.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


}