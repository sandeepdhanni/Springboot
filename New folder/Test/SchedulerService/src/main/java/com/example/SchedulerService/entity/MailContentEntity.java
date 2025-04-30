package com.example.SchedulerService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MAIL_DATA")
public class MailContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "MAIL_CC")
    private String mailCC;
    @Column(name = "MAIL_BCC")
    private String mailBcc;
    @Column(name = "SUBJECT")
    private String subject;
    @Lob
    @Column(name = "BODY", columnDefinition = "LONGTEXT")
    private String body;
    @Lob
    @Column(name = "JSON_DATA", columnDefinition = "LONGTEXT")
    private String jsonData;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATED_ON")
    private Timestamp createdOn;
    @Column(name = "UPDATED_ON")
    private Timestamp  updatedOn;
    @Column(name = "RECEIVED_ON")
    private Timestamp receivedOn;
    @Column(name = "PATH")
    private String path;
}
