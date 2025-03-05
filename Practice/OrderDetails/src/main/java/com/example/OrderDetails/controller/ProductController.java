package com.example.OrderDetails.controller;


import com.example.OrderDetails.dto.ProductResponse;
import com.example.OrderDetails.entity.ResponseModel;
import com.example.OrderDetails.response.CommonResponse;
import com.example.OrderDetails.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {



    @Autowired
    private ProductService productService;


    @GetMapping("/getAllproducts")
    public ResponseEntity<ResponseModel<Object>> getAllProducts()
    {
        List<ProductResponse> allProducts = productService.getAllProducts();
        return new CommonResponse<>().prepareSuccessResponseObject(allProducts, HttpStatus.OK);
    }
}
