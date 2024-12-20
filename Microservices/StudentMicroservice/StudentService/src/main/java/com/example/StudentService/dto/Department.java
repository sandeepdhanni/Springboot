package com.example.StudentService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}

