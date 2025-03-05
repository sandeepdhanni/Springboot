package com.example.OrderDetails.service;

import com.example.OrderDetails.entity.Order;
import com.example.OrderDetails.entity.OrderDetails;
import com.example.OrderDetails.entity.Products;
import com.example.OrderDetails.repository.OrderDetailsRepository;
import com.example.OrderDetails.repository.OrderRepository;
import com.example.OrderDetails.repository.ProductRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    private final String RAZORPAY_KEY = "your_razorpay_key";
    private final String RAZORPAY_SECRET = "your_razorpay_secret";

    public Order placeOrder(Integer userId, List<OrderDetails> orderDetailsList) throws RazorpayException {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(new Date());

        Double totalAmount = 0.0;
        for (OrderDetails details : orderDetailsList) {
            Products product = productRepository.findById(details.getProduct().getProductId()).orElse(null);
            if (product != null) {
                details.setOrder(order);
                details.setPrice(product.getPrice());
                totalAmount += product.getPrice() * details.getQuantity();
            }
        }

        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");

        // Create Razorpay Order
        RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);
        Map<String, Object> options = new HashMap<>();
        options.put("amount", totalAmount * 100);  // Amount in paise (1 INR = 100 paise)
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_" + new Random().nextInt(1000));

        Order razorpayOrder = razorpay.Orders.create(options);

        order.setPaymentId(razorpayOrder.get("id").toString());
        orderRepository.save(order);
        orderDetailsRepository.saveAll(orderDetailsList);

        return order;
    }

    public Order updateOrderStatus(Integer orderId, String paymentId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setPaymentId(paymentId);
            order.setStatus(status);
            orderRepository.save(order);
        }
        return order;
    }
}
