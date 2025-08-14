package com.ecommerce.inventory;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EcommerceInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceInventoryApplication.class, args);
	}

}
