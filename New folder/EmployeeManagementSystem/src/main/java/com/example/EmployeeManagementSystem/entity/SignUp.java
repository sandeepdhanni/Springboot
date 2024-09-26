package com.example.EmployeeManagementSystem.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SignUp {


    String firstname;
    String lastname;
    String email;
    String username;
    String password;
    private String role;


}
