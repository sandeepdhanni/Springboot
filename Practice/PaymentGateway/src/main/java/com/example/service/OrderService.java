package com.example.service;

import com.example.entity.Orders;
import com.example.repo.OrdersRepo;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrdersRepo orderRepo;
    @Value("${razorpay.key.id}")
    private String razorPayKey;
    @Value("${razorpay.secret.key}")
    private String razorPaySecret;

    private RazorpayClient client;



//    private static final String HMAC_SHA256 = "HmacSHA256";


//    public OrderService() throws Exception {
//        this.client = new RazorpayClient(razorPayKey, razorPaySecret);
//    }

//    public boolean verifyWebhookSignature(String payload, String razorpaySignature, String secret) throws Exception {
//        Mac sha256Hmac = Mac.getInstance(HMAC_SHA256);
//        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), HMAC_SHA256);
//        sha256Hmac.init(secretKey);
//
//        byte[] hash = sha256Hmac.doFinal(payload.getBytes());
//        String expectedSignature = Hex.encodeHexString(hash);
//
//        return expectedSignature.equals(razorpaySignature);
//    }



    public Orders createOrder(Orders order) throws Exception {
        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", order.getAmount() * 100);
        orderReq.put("currency", "INR");
        orderReq.put("receipt", order.getEmail());

        this.client = new RazorpayClient(razorPayKey, razorPaySecret);
        Order razorPayOrders= client.orders.create(orderReq);
        order.setRazorPayOrderId(razorPayOrders.get("id"));
        order.setOrderStatus(razorPayOrders.get("status"));
        order.setOrderDate(new Date());

        orderRepo.save(order);
        return order;
    }

    public Payment getPaymentDetails(String razorPayPaymentId) throws Exception {
        Payment payment = client.payments.fetch(razorPayPaymentId);
        return payment;
    }


//    public Orders createOrder(Orders order) throws Exception {
//        JSONObject orderReq = new JSONObject();
//        //orderReq.put("Payment ID", order.getRazorPayPaymentId());
//        orderReq.put("Bank RRN", order.getBank());
//        orderReq.put("Customer detail", order.getPhoneNumber());
//        order.setOrderDate(new Date());
//        orderReq.put("Amount", order.getAmount());
//        orderReq.put("Status", order.getOrderStatus());
//
//        this.client = new RazorpayClient(razorPayKey, razorPaySecret);
//        Payment razorPayOrders= client.payments.createJsonPayment(orderReq);
//        log.info("razorPay payment {}",razorPayOrders);
////        order.setRazorPayOrderId(razorPayOrders("id"));
////        order.setOrderStatus(razorPayOrders.get("status"));
//        order.setOrderDate(new Date());
//
//        orderRepo.save(order);
//        return order;
//    }


}

