package com.example.KafkaDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/message")
@Slf4j
public class MessageController {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public MessageController(KafkaTemplate<String,String> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    @PostMapping
    public void publish(@RequestBody MessageRequest request){
        log.info("entered controller{}", request);
        CompletableFuture<SendResult<String, String>> sam = kafkaTemplate.send("sam", request.message());
        log.info("topic: {}", sam);
    }
}
