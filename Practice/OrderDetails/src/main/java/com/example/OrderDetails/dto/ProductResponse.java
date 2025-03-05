package com.example.OrderDetails.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {


    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String productImage;

}
