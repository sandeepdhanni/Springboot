package com.example.Products.controller;

import com.example.Products.entity.Product;
import com.example.Products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productPrice") double productPrice,
            @RequestParam("productQuantity") int productQuantity,
            @RequestParam("productImage") MultipartFile productImage) {
        try {
            Product product = new Product();
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductQuantity(productQuantity);
            product.setProductImage(productImage.getBytes());

            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product image");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
}
