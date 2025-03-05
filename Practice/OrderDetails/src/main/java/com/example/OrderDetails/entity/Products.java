package com.example.OrderDetails.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Products {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    @Lob
    @Column(name = "productImage")
    private byte[] productImage;
}

