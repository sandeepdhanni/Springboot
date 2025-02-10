package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String userName;
    private int taskCount;

}
