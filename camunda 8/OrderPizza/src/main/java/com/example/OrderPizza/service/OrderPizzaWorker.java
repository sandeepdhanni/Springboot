package com.example.OrderPizza.service;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
public class OrderPizzaWorker {

//    @JobWorker(type = "orderPizza")
//    public void handleOrderPizza(final ActivatedJob job, final ZeebeClient client) {
//        log.info("job : "+job);
//        // Retrieve input variables from the job
//        String customerName = (String) job.getVariablesAsMap().get("customerName");
//        String pizzaType = (String) job.getVariablesAsMap().get("pizzaType");
//        Boolean paymentStatus = (Boolean) job.getVariablesAsMap().get("paymentStatus");
//
//        log.info("Processing pizza order for " + customerName);
//
//        // Simulate order processing
//        String orderId = UUID.randomUUID().toString();
//        int deliveryTimeEstimate = ThreadLocalRandom.current().nextInt(20, 45);
//
//        log.info("Delivery estate time is : "+deliveryTimeEstimate);
//
//        // Prepare output variables
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("orderId", orderId);
//        variables.put("deliveryTimeEstimate", deliveryTimeEstimate);
//        variables.put("orderStatus", Boolean.TRUE.equals(paymentStatus) ? "Confirmed" : "Payment Pending");
//
//        // Complete the job with output variables
//        client.newCompleteCommand(job.getKey()).variables(variables).send().join();
//
//        log.info("Order " + orderId + " processed. Status: " + variables.get("orderStatus"));
//    }


    @JobWorker(type = "orderPizza")
    public void handleOrderPizza(final ActivatedJob job, final ZeebeClient client) {
        log.info("job : " + job);

        // Retrieve input variables
        String customerName = (String) job.getVariablesAsMap().get("customerName");
        String pizzaType = (String) job.getVariablesAsMap().get("pizzaType");
        Boolean paymentStatus = (Boolean) job.getVariablesAsMap().get("paymentStatus");

        log.info("Processing pizza order for " + customerName);

        // Simulate order processing
        String orderId = UUID.randomUUID().toString();
        int deliveryTimeEstimate = ThreadLocalRandom.current().nextInt(20, 45);

        // Set output variables
        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);
        variables.put("deliveryTimeEstimate", deliveryTimeEstimate);
        variables.put("orderStatus", Boolean.TRUE.equals(paymentStatus) ? "Confirmed" : "Payment Pending");

        // Complete the job
        client.newCompleteCommand(job.getKey())
                .variables(variables)
                .send()
                .join();

        log.info("Order " + orderId + " processed. Status: " + variables.get("orderStatus"));

        // Simulate external system acknowledgment (like after delivery system logs it)
        client.newPublishMessageCommand()
                .messageName("order_created")       // Must match BPMN
                .correlationKey("created")            // Used to match this message with the process instance
                .variables(Map.of("orderReceivedTime", LocalDateTime.now().toString()))
                .send()
                .join();

        log.info("Published message: orderReceived for orderId: " + orderId);
    }

}
