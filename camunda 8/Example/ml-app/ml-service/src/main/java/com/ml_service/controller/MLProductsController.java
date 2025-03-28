package com.ml_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml_service.dto.MLProductsdto;
import com.ml_service.service.MLproductservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class MLProductsController {
    private final  MLproductservice mLproductservice;

    public MLProductsController(MLproductservice mLproductservice) {
        this.mLproductservice = mLproductservice;
    }

    @PostMapping("/save")
    public ResponseEntity<String> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productPrice") double productPrice,
            @RequestParam("productQuantity") String productQuantity,
            @RequestParam("productImage") MultipartFile productImage
    ){
        try {
            List<Integer> quantities = Arrays.stream(productQuantity.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        MLProductsdto productsdto = new MLProductsdto(null,productName,productPrice,quantities,null);
        String responseMessage = mLproductservice.addProduct(productsdto,productImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }catch (IOException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product image");
    }

    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<MLProductsdto>> getAllProducts(){
        List<MLProductsdto> products = mLproductservice.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
