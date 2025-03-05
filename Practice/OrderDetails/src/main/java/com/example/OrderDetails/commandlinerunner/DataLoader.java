package com.example.OrderDetails.commandlinerunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class DataLoader {

    @Autowired
    private ProductRepository productRepository;

    @Bean
    public CommandLineRunner saveProducts() {
        return args -> {

            List<Products> productsList = new ArrayList<>();
            Products product1 = new Products(1, "Canon R100 Mirrorless", "This Canon R100 Mirrorless Camera features a RF-S 18-45 mm lens"
                    , 49500.00, 1, loadImage("camera.jpeg"));
            Products product2 = new Products(2, "DELL SE-Series 68.58 cm", "Stylish and Portable Design", 12568.99, 1, loadImage("dellLaptop.jfif"));
            Products product3 = new Products(3, "Apple iPhone 15", "Experience the iPhone 15 – your dynamic companion.", 59500.00, 1, loadImage("iphone15pro.jfif"));
            Products product4 = new Products(4, "LG 32LMBPTC 80 cm", "You can conveniently browse, stream, and watch content on the LG Smart LED TV.", 29500.00, 1, loadImage("LgTv.jfif"));
            Products product5 = new Products(5, "Apple MacBook AIR Apple M2", "Charged in a blazing-fast speed with the next-level M2 chip", 84500.00, 1, loadImage("mackBook.webp"));
            Products product6 = new Products(6, "HexaGear Collar Microphone", "Collar Microphone Works well with DSLR Camera", 890.36, 1, loadImage("microphones.jpeg"));
            Products product7 = new Products(7, "Apple MC747HN/A charger", "These sellers are Apple-authorized resellers", 1500.99, 1, loadImage("mackBookCharger.jfif"));

            productsList.add(product1);
            productsList.add(product2);
            productsList.add(product3);
            productsList.add(product4);
            productsList.add(product5);
            productsList.add(product6);
            productsList.add(product7);

            for (Products product : productsList) {
                Products existingProduct = productRepository.findByProductId(product.getProductId());
                if (existingProduct == null) {
                    productRepository.save(product);
                    log.info("Inserted: " + product.getName());
                } else {
                    log.info("Product ID " + product.getProductId() + " already exists. Skipping...");
                }
            }
        };

    }
    private byte[] loadImage(String imageName) throws IOException {
        Path imagePath = Paths.get("src/main/resources/image/" + imageName);
        return Files.readAllBytes(imagePath);
    }

}

