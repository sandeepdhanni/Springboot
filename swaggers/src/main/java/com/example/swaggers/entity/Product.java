package com.example.swaggers.entity;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {


    private int id;
    private String name;
    private double price;

}
