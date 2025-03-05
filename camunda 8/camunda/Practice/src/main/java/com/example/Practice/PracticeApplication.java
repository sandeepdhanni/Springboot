package com.example.Practice;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Deployment(resources = "classpath:one.bpmn")
public class PracticeApplication  {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }

//    @Autowired
//    private ZeebeClient client;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String key = "code"; // This must match the variable value in Camunda
//        client.newPublishMessageCommand().messageName("Pizaa-received").correlationKey(key).send().exceptionally(throwable -> {
//            throw new RuntimeException("error");
//        });
//    }
}
