package com.example.studentmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.studentmanagement.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Метод для пошуку студентів за ім'ям
    List<Student> findByName(String name);
}
