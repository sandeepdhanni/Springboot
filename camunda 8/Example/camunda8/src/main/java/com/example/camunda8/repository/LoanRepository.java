package com.example.camunda8.repository;

import com.example.camunda8.entity.LoanProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanProcess, Integer> {
    Optional<LoanProcess> findByMail(String mail);
}
