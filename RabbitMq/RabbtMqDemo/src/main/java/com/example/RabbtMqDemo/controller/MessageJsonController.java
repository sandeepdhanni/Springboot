package com.example.RabbtMqDemo.controller;

import com.example.RabbtMqDemo.dto.User;
import com.example.RabbtMqDemo.publisher.RabbitMQJsonProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {

    private RabbitMQJsonProducer jsonProducer;


    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(User user){
        jsonProducer.sendJsonMessage(user);
        return ResponseEntity.ok("Json message sent to rabbitmq ...");
    }
}
