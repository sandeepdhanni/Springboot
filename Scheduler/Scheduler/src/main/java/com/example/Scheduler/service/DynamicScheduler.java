package com.example.Scheduler.service;

import com.example.Scheduler.entity.SchedulerConfig;
import com.example.Scheduler.repository.SchedulerConfigRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.ZoneId;

@Slf4j
@Configuration
@EnableScheduling
public class DynamicScheduler implements SchedulingConfigurer {

    private SchedulerConfigRepo repository;

    public DynamicScheduler(SchedulerConfigRepo repository) {
        this.repository = repository;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                this::printMessage3,
                triggerContext -> {
                    SchedulerConfig config = repository.findById(1).orElse(null);
                    log.info("id is : {}", config);
                    if (config == null) return null;

                    String cron = config.getCronExpression();
                    log.info("cron value: {}", cron);
                    ZoneId zoneId = ZoneId.of(config.getTimeZone());
                    log.info("zone value : {}", zoneId);
                    CronTrigger trigger = new CronTrigger(cron, zoneId);
                    log.info("trigger values : {}", trigger);
                    return trigger.nextExecution(triggerContext);
                }
        );
    }
    public void printMessage3() {
        log.info(Thread.currentThread().getName() + " The data is coming from the database");
    }
}
