package com.example.Payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @GetMapping("/payment/{id}")
    public String getPayment(@PathVariable("id") String id) {
        return "Payment with ID: " + id;
    }
}
