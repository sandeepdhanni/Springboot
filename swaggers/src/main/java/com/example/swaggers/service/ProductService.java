package com.example.swaggers.service;

import com.example.swaggers.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> productList = new ArrayList<>();

    // Add product
    public Product addProduct(Product product) {
        productList.add(product);
        return product;
    }

    // Get product by ID
    public Product getProductById(int id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productList;
    }
}
