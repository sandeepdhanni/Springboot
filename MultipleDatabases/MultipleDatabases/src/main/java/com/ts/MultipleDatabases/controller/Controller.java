package com.ts.MultipleDatabases.controller;

import com.ts.MultipleDatabases.entity.mysql.Student;
import com.ts.MultipleDatabases.repository.h2.H2Repo;
import com.ts.MultipleDatabases.repository.mysql.MysqlRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class Controller {

    @Autowired
    private MysqlRepo mysqlRepo;
    @Autowired
    private H2Repo h2Repo;

    @GetMapping("/get")
    public String value(){
        return "hello";
    }

    //save same student in two databases
    @PostMapping("/save")
    public List<Student> savestudent(@RequestBody Student student){
        List<Student> students = new ArrayList<>();
        Student student1=  mysqlRepo.save(student);
        Student student2=h2Repo.save(student);
        students.add(student1);
        students.add(student2);
        return students;
    }
}
