package com.example.SchedulerService.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MailContentDto {
    private String from;
    private String cc;
    private String bcc;
    private String subject;
    private Date receivedDate;
    private String body;
}
