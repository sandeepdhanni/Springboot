package com.javatechie.controller;

import com.javatechie.dto.request.OrderRequest;
import com.javatechie.dto.response.OrderResponse;
import com.javatechie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Endpoint to place an order
    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            log.info("entered try{}", orderRequest);
            OrderResponse orderResponse = orderService.placeAnOrder(orderRequest);
            log.info("returned response{}", orderResponse);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to confirm an order
    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<OrderResponse> confirmOrder(@PathVariable String orderId) {
        try {
            OrderResponse orderResponse = orderService.confirmOrder(orderId);
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
