package com.example.RabbtMqDemo;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class RabbtMqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbtMqDemoApplication.class, args);
	}

}
