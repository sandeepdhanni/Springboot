package com.ml_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLProductsdto {

    private Integer product_id;
    private String productName;
    private double price;
    private List<Integer> quantity;
    private byte[] product_image;
}
