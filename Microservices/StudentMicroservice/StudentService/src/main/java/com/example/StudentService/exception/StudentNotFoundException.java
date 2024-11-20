package com.example.StudentService.exception;


public class StudentNotFoundException extends RuntimeException {
    private

    final Long studentId;

    public

    StudentNotFoundException(Long studentId) {
        super("Student with ID " + studentId + " not found");
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}

