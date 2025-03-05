package com.example.OrderDetails.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import com.example.OrderDetails.dto.ProductResponse;
import com.example.OrderDetails.entity.Products;
import com.example.OrderDetails.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productsRepo;

    public List<ProductResponse> getAllProducts() {

        List<Products> all = (List<Products>) productsRepo.findAll();
        return	all.stream()
                .map(products -> {
                    ProductResponse productResponse = new ProductResponse();
                    productResponse.setName(products.getName());
                    productResponse.setDescription(products.getDescription());
                    productResponse.setPrice(products.getPrice());
                    productResponse.setStockQuantity(products.getStockQuantity());

                    byte[] festivalImage = products.getProductImage();
                    if (festivalImage != null) {
                        String encodeToString = Base64.getEncoder().encodeToString(festivalImage);
                        productResponse.setProductImage(encodeToString);
                    }
                    return productResponse;
                })
                .collect(Collectors.toList());
    }
}

