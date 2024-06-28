package com.example.CRUD1.controller;


import com.example.CRUD1.entity.Employee;
import com.example.CRUD1.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getEmployee")
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/getEmployeeById/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping("/createEmployee")
    public void createEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
    }


    @PutMapping("/updateEmployee/{id}")
    public void updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        employeeService.update(id, employee);
    }

    @DeleteMapping("/delete/record/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
    }

}


