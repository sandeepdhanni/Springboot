package com.example.CRUD1.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeModel {

    private String firstname;
    private String lastname;
    private String department;
    private double salary;
}