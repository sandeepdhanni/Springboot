package com.example.entity;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orderPayment")
@Data
@RequiredArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO
    )
    private Integer orderId;
    private String name;
    private String email;
    private Integer attempts;
    private String phoneNumber;
    private String orderStatus;
    private Integer amount;
    private Date orderDate;
    private String razorPayOrderId;
    private String userId;


}


