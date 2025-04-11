package com.example.CRUDProduct.controller;

import com.example.CRUDProduct.entity.Product;
import com.example.CRUDProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }


    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }


//    @GetMapping("/getProducts/{id}")
//    public Product getEmployeeById(@PathVariable Long id) {
//        return productService.findById(id);
//    }



    @PutMapping("/updateProducts/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productService.update(id, product);
    }


    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
