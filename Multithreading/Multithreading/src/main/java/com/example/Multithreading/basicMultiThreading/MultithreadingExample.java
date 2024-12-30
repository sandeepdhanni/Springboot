package com.example.Multithreading.basicMultiThreading;

public class MultithreadingExample {
    public static void main(String[] args) {
        // Simulating multiple tasks
        Thread order1 = new OrderProcessing("Pizza");
        Thread order2 = new OrderProcessing("Burger");
        Thread payment1 = new PaymentProcessing("Payment123");
        Thread notification = new NotificationService("Your food is on the way!");

        // Start threads
        order1.start();
        order2.start();
        payment1.start();
        notification.start();

        // Wait for all threads to complete
        try {
            order1.join();
            order2.join();
            payment1.join();
            notification.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed!");
    }
}


class OrderProcessing extends Thread {
    private String orderName;

    public OrderProcessing(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public void run() {
        System.out.println("Processing order: " + orderName);
        try {
            Thread.sleep(2000); // Simulate processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed: " + orderName);
    }
}

class PaymentProcessing extends Thread {
    private String paymentId;

    public PaymentProcessing(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public void run() {
        System.out.println("Processing payment: " + paymentId);
        try {
            Thread.sleep(1000); // Simulate payment time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Payment successful for: " + paymentId);
    }
}

class NotificationService extends Thread {
    private String message;

    public NotificationService(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println("Sending notification: " + message);
        try {
            Thread.sleep(500); // Simulate notification delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Notification sent: " + message);
    }
}


