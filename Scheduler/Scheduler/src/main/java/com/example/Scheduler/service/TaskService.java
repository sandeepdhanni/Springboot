//package com.example.Scheduler.service;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TaskService {
//
//    // This method will be scheduled to run every 5 seconds
//    @Scheduled(fixedRate = 5000)
//    public void printMessage() {
//        System.out.println("Hello! This message is printed every 5 seconds.");
//    }
//
//    // This method will run every minute at the 30th second
//    @Scheduled(cron = "30 * * * * *")
//    public void printCronMessage() {
//        System.out.println("Cron job: This runs every minute at the 30th second.");
//    }
//
//    // This method runs once after the application has started, after a 10-second delay
//    @Scheduled(initialDelay = 10000, fixedRate = 5000)
//    public void startAfterDelay() {
//        System.out.println("This message starts after a 10-second delay and then runs every 5 seconds.");
//    }
//
//    @Scheduled(fixedRate = 5000)
//    public void printMessage1() {
//        System.out.println(Thread.currentThread().getName() + ": Hello! This message is printed every 5 seconds.");
//    }
//
//    @Scheduled(cron = "35 * * * * *")
//    public void printCronMessage1() {
//        System.out.println(Thread.currentThread().getName() + ": Cron job: This runs every minute at the 30th second.");
//    }
//
//    @Scheduled(cron = "35 * * * * *",zone = "Asia/Hyderabad")
//    public void printMessage3(){
//        System.out.println(Thread.currentThread().getName() + "The data is comming from the database");
//    }
//}
