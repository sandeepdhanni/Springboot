package com.example.CRUD1.service;

import com.example.CRUD1.entity.Employee;
import com.example.CRUD1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private  EmployeeRepository employeeRepository;


    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }



    public void update(Long id, Employee employeeDetails) {
        if (employeeRepository.existsById(id)) {
//            Employee.builder().SetId(ïd).setName("name").setEmail(email).build()
            employeeDetails.setId(id);
            employeeRepository.save(employeeDetails);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }


    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}


