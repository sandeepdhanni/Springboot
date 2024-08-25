package com.ts.MultipleDatabases.repository.h2;

import com.ts.MultipleDatabases.entity.mysql.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2Repo extends JpaRepository<Student,Long> {
}
