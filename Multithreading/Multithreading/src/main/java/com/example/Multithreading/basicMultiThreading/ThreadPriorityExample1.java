package com.example.Multithreading.basicMultiThreading;

public class ThreadPriorityExample1 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName()+" says hii!!");
        Thread one=new Thread(()->{
            System.out.println("Thread one says hii as well");
        });
        one.setPriority(Thread.MAX_PRIORITY);
        one.start();
    }
}
