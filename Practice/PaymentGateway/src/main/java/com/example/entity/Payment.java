package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double amount;
    private String status;
    private String razorPayPaymentId;
    private String paymentMethod;
    private String bank;
    private String wallet;
    private String vpa;
    private String currency;
    private Date paymentDate;
    private String razorPayOrderId;




}
