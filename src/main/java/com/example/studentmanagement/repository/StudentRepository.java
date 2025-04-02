package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Student save(Student student);
    void deleteById(Long id);
    Student update(Long id, Student student);
} 