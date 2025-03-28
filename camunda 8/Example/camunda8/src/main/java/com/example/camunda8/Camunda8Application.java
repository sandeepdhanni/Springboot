package com.example.camunda8;

import io.camunda.zeebe.spring.client.EnableZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Camunda8Application {

	public static void main(String[] args) {
		SpringApplication.run(Camunda8Application.class, args);
	}

}
