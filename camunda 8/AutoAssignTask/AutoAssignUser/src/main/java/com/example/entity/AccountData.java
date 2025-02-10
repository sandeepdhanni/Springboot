package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AccountData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String instanceId;
	private String accountNumber;
	private long accountValue;
	private String assignedGroup;
	private String assignedUser;
	private String status;
	private String taskId;
	
	
}
