package com.example.exportdatafromdb_toexcel_and_pdf.repository;

import com.example.exportdatafromdb_toexcel_and_pdf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
