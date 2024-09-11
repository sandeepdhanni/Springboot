package com.example.swaggers.controller;

import com.example.swaggers.entity.Product;
import com.example.swaggers.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api(value = "Product Management System", description = "Operations pertaining to products in the system")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ApiOperation(value = "Add a new product", response = Product.class)
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a product by its ID", response = Product.class)
    public Product getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping
    @ApiOperation(value = "View a list of available products", response = List.class)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}