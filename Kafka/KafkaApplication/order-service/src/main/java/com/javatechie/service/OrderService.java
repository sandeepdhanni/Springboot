package com.javatechie.service;

import com.javatechie.dto.enums.OrderStatus;
import com.javatechie.dto.request.OrderRequest;
import com.javatechie.dto.response.OrderResponse;
import com.javatechie.entity.OrderEvent;
import com.javatechie.publisher.OrderEventKafkaPublisher;
import com.javatechie.repository.OrderEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {
    private final OrderEventRepository repository;

    private final OrderEventKafkaPublisher publisher;

    @Autowired
    public OrderService(OrderEventRepository repository, OrderEventKafkaPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    // Handle order creation
    public OrderResponse placeAnOrder(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString().split("-")[0];
        log.info("entered service{}", orderId);
        orderRequest.setOrderId(orderId);
        //do request validation and real business logic
        //save that event and publish kafka messages
        OrderEvent orderEvent=new OrderEvent(orderId,OrderStatus.CREATED,"Order created successfully", LocalDateTime.now());
        log.info("order created"+orderEvent);
        saveAndPublishEvents(orderEvent);
        log.info("order saved");
        return new OrderResponse(orderId, OrderStatus.CREATED);
    }

    // Handle order confirmation
    public OrderResponse confirmOrder(String orderId) {
        OrderEvent orderEvent=new OrderEvent(orderId,OrderStatus.CONFIRMED,"Order confirmed successfully", LocalDateTime.now());
        saveAndPublishEvents(orderEvent);
        return new OrderResponse(orderId, OrderStatus.CONFIRMED);
    }

    private void saveAndPublishEvents(OrderEvent orderEvent){
        log.info("entered publisher");
        repository.save(orderEvent);
        publisher.sendOrderEvent(orderEvent);
    }


}
