package com.ts.MultipleDatabases.repository.mysql;

import com.ts.MultipleDatabases.entity.mysql.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlRepo extends JpaRepository<Student,Long> {
}
