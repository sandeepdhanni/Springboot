package com.example.Multithreading.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {

    // Simulate fetching user data
    public static CompletableFuture<String> getUserData() {
        return CompletableFuture.supplyAsync(() -> {
            // Simulating a delay in fetching user data
            try {
                Thread.sleep(1000); // 1 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "User data: John Doe";
        });
    }

    // Simulate fetching order data
    public static CompletableFuture<String> getOrderData() {
        return CompletableFuture.supplyAsync(() -> {
            // Simulating a delay in fetching order data
            try {
                Thread.sleep(1500); // 1.5 second delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Order data: Laptop, Phone";
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Start both tasks asynchronously
        CompletableFuture<String> userFuture = getUserData();
        CompletableFuture<String> orderFuture = getOrderData();

        // Combine both results when both are complete
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(userFuture, orderFuture);

        // Wait for both futures to complete and then process the results
        combinedFuture.thenRun(() -> {
            try {
                // Fetch the results after both tasks are completed
                String userData = userFuture.get();  // Waits for the user data
                String orderData = orderFuture.get();  // Waits for the order data

                // Process and combine the results
                System.out.println(userData);
                System.out.println(orderData);
                System.out.println("Data retrieval complete!");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).join(); // Wait for the combined task to finish
    }


   //You can also use thenCombine() to combine the results of two CompletableFuture objects:
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> userFuture = getUserData();
//        CompletableFuture<String> orderFuture = getOrderData();
//
//        userFuture.thenCombine(orderFuture, (userData, orderData) -> {
//            return userData + "\n" + orderData + "\nData retrieval complete!";
//        }).thenAccept(System.out::println).join();
//    }

}