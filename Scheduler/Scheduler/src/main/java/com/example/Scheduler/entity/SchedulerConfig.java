package com.example.Scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "scheduler_config")
public class SchedulerConfig {

    @Id
    private int id;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "time_zone")
    private String timeZone;

}
