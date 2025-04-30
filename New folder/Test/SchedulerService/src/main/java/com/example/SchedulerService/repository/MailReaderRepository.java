package com.example.SchedulerService.repository;

import com.example.SchedulerService.entity.MailContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailReaderRepository extends JpaRepository<MailContentEntity,Long> {
}
