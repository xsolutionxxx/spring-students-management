package com.example.studentmanagement.service;

import java.util.List;
import java.util.Optional;

import com.example.studentmanagement.model.Student;

public interface StudentService {
    List<Student> getAllStudents();

    Optional<Student> getStudentById(Long id);
}
