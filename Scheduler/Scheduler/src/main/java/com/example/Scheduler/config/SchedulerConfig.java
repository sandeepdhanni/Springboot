package com.example.Scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);  // Set the pool size (number of threads)
        scheduler.setThreadNamePrefix("scheduled-task-");  // Set thread name prefix for easier identification
        scheduler.setDaemon(true);  // Set to true if you want the threads to be daemon threads
        return scheduler;
    }
}
