package com.example.repo;

import com.example.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByRazorPayPaymentId(String razorPayPaymentId);

}
