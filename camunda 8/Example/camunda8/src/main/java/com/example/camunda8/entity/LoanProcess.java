package com.example.camunda8.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "LOAN_PROCESS")
@Getter
@Setter
public class LoanProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "mail")
    private String mail;
    @Column(name = "gender")
    private String gender;
    @Column(name = "loan_amount")
    private Double loan_amount;
    @Column(name = "credit_score")
    private Long credit_score;
}
