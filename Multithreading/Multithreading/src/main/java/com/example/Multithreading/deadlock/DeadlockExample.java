package com.example.Multithreading.deadlock;

public class DeadlockExample {
    private static final Object LOCK1=new Object();
    private static final Object LOCK2=new Object();
    public static void main(String[] args) {
        Thread thread1=new Thread(()->{
            synchronized (LOCK1){
                System.out.println("Thread 1: Locked Lock1");
                try{
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                synchronized (LOCK2) {
                    System.out.println("Thread 1: Locked Lock2");
                }
            }
        });
        Thread thread2=new Thread(()->{
            synchronized (LOCK2){
                System.out.println("Thread 2: Locked Lock2");
                try{
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                synchronized (LOCK1) {
                    System.out.println("Thread 2: Locked Lock1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
