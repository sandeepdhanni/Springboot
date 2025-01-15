package com.example.workflow.rest;

import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    private ZeebeClient client;

    @PostMapping("/msg")
    public void ok(){
        System.out.println("msg entered!!!!!!");
        client.newPublishMessageCommand().messageName("Pizaa-received").correlationKey("code").send().exceptionally(throwable -> {
            throw new RuntimeException("error");
        });
    }
}
