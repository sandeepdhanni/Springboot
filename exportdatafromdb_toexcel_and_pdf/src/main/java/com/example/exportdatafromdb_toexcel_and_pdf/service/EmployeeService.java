package com.example.exportdatafromdb_toexcel_and_pdf.service;

import com.example.exportdatafromdb_toexcel_and_pdf.entity.Employee;
import com.example.exportdatafromdb_toexcel_and_pdf.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
