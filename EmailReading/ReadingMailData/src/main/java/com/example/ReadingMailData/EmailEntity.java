package com.example.ReadingMailData;

import com.google.gson.Gson;
import jakarta.persistence.*;

import java.util.Map;

@Entity
class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String data;

    public EmailEntity(Map<String, Object> mailData) {
        this.data = new Gson().toJson(mailData);
    }

    public EmailEntity() {}
}
