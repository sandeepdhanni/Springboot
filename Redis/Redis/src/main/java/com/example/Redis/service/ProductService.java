package com.example.Redis.service;



import com.example.Redis.entity.Product;
import com.example.Redis.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;

    public Product save(Product product) {
        log.info("saved product{}", product);
        return productRepository.save(product);

    }

    @Cacheable(value = "product", key = "#id")
    public Optional<Product> findById(Integer id) {
        log.info("Fetching from DB for id: " + id);
        return productRepository.findById(id);
    }

    @CacheEvict(value = "product", key = "#id")
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}

