package com.example.Actuators.controller;

import com.example.Actuators.entity.Product;
import com.example.Actuators.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    // get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // create new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    // update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            return ResponseEntity.ok(productService.saveProduct(product));
        }
        return ResponseEntity.notFound().build();
    }

    // delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // search products by name
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }

    // get product count
    @GetMapping("/count")
    public long getProductCount() {
        return productService.getAllProducts().size();
    }

    // get products sorted by price
    @GetMapping("/sorted")
    public List<Product> getProductsSortedByPrice() {
        return productService.getAllProducts().stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());
    }

    // get the cheapest product
    @GetMapping("/cheapest")
    public Product getCheapestProduct() {
        return productService.getAllProducts().stream()
                .min(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }

    // get the most expensive product
    @GetMapping("/expensive")
    public Product getMostExpensiveProduct() {
        return productService.getAllProducts().stream()
                .max(Comparator.comparing(Product::getPrice))
                .orElse(null);
    }
}

