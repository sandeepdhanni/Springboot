package com.example.CRUDRepo.service;

import com.example.CRUDRepo.entity.Product;
import com.example.CRUDRepo.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public Product saveProduct(Product product){
        return productRepo.save(product);
    }

    public Product getProductById(Long id){
        return productRepo.findById(id).orElse(null);
    }

    public Iterable<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public void deleteProduct(Long id){
        productRepo.deleteById(id);
    }

    public List<Product> getProductsByPriceGreaterThan(Double price) {
        return productRepo.findProductByPriceGreaterThan(price);
    }

    public Product getProductByName(@PathVariable String name){
        return productRepo.findProductByName(name);
    }

    public Long countProduct(){
        return productRepo.countProducts();
    }


}
