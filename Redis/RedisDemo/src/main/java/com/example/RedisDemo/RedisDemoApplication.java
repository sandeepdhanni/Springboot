package com.example.RedisDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/user")
public class RedisDemoApplication {

		@Autowired
		private UserService dao;

		@PostMapping
		public User save(@RequestBody User user) {
			return dao.save(user);
		}

		@GetMapping
		public List<User> getAllProducts() {
			return dao.findAll();
		}

		@GetMapping("/{id}")
		public User findProduct(@PathVariable String id) {
			return dao.findProductById(id);
		}

		@DeleteMapping("/{id}")
		public String remove(@PathVariable String id) {
			return dao.deleteProduct(id);
		}


	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

}
