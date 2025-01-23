package com.example.Multithreading.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RealWorldExampleCompactibleFuture {
    // Simulate checking product availability in the inventory
    public static CompletableFuture<Boolean> checkInventory(String product) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate a delay for checking inventory
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("Checked inventory for: " + product);
            return true; // Assume product is available
        });
    }

    // Simulate processing the payment
    public static CompletableFuture<Boolean> processPayment(double amount) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate a delay for payment processing
            try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("Processed payment of: $" + amount);
            return true; // Assume payment is successful
        });
    }

    // Simulate scheduling shipment for delivery
    public static CompletableFuture<String> scheduleShipment(String address) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulate a delay for scheduling shipment
            try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("Scheduled shipment to: " + address);
            return "Shipment scheduled successfully";
        });
    }

    // Combine results of all tasks and finalize the order
    public static CompletableFuture<String> placeOrder(String product, double amount, String address) {
        // Check inventory, process payment, and schedule shipment in parallel
        CompletableFuture<Boolean> inventoryCheck = checkInventory(product);
        CompletableFuture<Boolean> paymentProcess = processPayment(amount);
        CompletableFuture<String> shipmentSchedule = scheduleShipment(address);

        // Combine results
        return CompletableFuture.allOf(inventoryCheck, paymentProcess, shipmentSchedule)
                .thenApplyAsync(v -> {
                    try {
                        // Check all results
                        boolean isInventoryAvailable = inventoryCheck.get();
                        boolean isPaymentProcessed = paymentProcess.get();
                        String shipmentStatus = shipmentSchedule.get();

                        if (isInventoryAvailable && isPaymentProcessed) {
                            return "Order placed successfully. " + shipmentStatus;
                        } else {
                            return "Order failed due to inventory or payment issues.";
                        }
                    } catch (Exception e) {
                        return "Error in processing the order: " + e.getMessage();
                    }
                });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Simulate placing an order
        CompletableFuture<String> orderResult = placeOrder("Laptop", 1200.00, "123 Main St, Springfield");
        System.out.println(orderResult.get()); // Output will depend on the result of each step
    }
}
