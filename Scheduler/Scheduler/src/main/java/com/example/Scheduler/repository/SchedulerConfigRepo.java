package com.example.Scheduler.repository;

import com.example.Scheduler.entity.SchedulerConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerConfigRepo extends JpaRepository<SchedulerConfig, Integer> {
}