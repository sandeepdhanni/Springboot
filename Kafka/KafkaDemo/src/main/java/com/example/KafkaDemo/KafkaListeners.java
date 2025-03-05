package com.example.KafkaDemo;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "sam",groupId = "groupId")
    void listener(String data){
        System.out.println("Listener received: " + data);
    }
}
