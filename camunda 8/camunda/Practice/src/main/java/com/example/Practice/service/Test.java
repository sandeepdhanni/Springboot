package com.example.Practice.service;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    private ZeebeClient client;

//    @PostMapping("/test")
//    public void sam() {
//        System.out.println("enter");
//        String key = "code"; // This must match the variable value in Camunda
//        client.newPublishMessageCommand().messageName("Pizaa-received").correlationKey(key).send().exceptionally(throwable -> {
//            throw new RuntimeException("error");
//        });
//    }



    @PostMapping("/test")
    public String sam() {
        System.out.println("Enter: Sending message to Camunda");

        String key = "code"; // Ensure this matches the correlation key in the BPMN model
        try {
            client.newPublishMessageCommand()
                    .messageName("Pizaa-received") // Ensure this matches the BPMN model
                    .correlationKey(key) // This must match a variable in the process
                    .send()
                    .join(); // Wait for completion

            return "Message sent successfully!";
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
            e.printStackTrace(); // Print detailed stack trace
            return "Failed to send message: " + e.getMessage();
        }
    }



}
