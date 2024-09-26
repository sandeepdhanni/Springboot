package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.entity.SignUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignUpRepository extends JpaRepository<SignUp, Long> {
    Optional<SignUp> findByEmail(String email);
}
