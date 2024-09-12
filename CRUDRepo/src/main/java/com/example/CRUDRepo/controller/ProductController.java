package com.example.CRUDRepo.controller;


import com.example.CRUDRepo.entity.Product;
import com.example.CRUDRepo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public Product createProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }


    @GetMapping("/{id}")
    public Product saveProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return productService.saveProduct(product);
        }
        return null;
    }


    @GetMapping
    public Iterable<Product> saveAllProducts(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
         productService.deleteProduct(id);
         return "product deleted with id:"+id;
    }


    @GetMapping("/price/{price}")
    public List<Product> getProductByPrice(@PathVariable Double price){
        return productService.getProductsByPriceGreaterThan(price);
    }


    @GetMapping("/name/{name}")
    public Product getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @GetMapping
    public Long countProducts(){
        return  productService.countProduct();
    }



}
