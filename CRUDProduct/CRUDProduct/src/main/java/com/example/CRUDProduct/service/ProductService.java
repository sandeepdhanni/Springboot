package com.example.CRUDProduct.service;

import com.example.CRUDProduct.entity.Product;
import com.example.CRUDProduct.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


    public void update(Long id, Product productDetails) {
        if (productRepository.existsById(id)) {
            productDetails.setId(id);
            productRepository.save(productDetails);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}