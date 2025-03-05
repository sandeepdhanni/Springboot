package com.example.OrderDetails.controller;

import com.example.OrderDetails.entity.Order;
import com.example.OrderDetails.entity.OrderDetails;
import com.example.OrderDetails.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody List<OrderDetails> orderDetailsList, @RequestParam Integer userId) {
        try {
            Order order = orderService.placeOrder(userId, orderDetailsList);
            return new CommonResponse<>().prepareSuccessResponseObject(order, HttpStatus.OK);
        } catch (Exception e) {
            return new CommonResponse<>().prepareFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateOrderStatus(@RequestParam Integer orderId, @RequestParam String paymentId, @RequestParam String status) {
        Order order = orderService.updateOrderStatus(orderId, paymentId, status);
        return new CommonResponse<>().prepareSuccessResponseObject(order, HttpStatus.OK);
    }
}