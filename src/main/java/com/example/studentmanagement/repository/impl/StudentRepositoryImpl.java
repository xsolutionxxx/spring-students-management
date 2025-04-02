package com.example.studentmanagement.repository.impl;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<Long, Student> students = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    @Override
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idGenerator.getAndIncrement());
        }
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    @Override
    public void deleteById(Long id) {
        students.remove(id);
    }

    @Override
    public Student update(Long id, Student student) {
        if (!students.containsKey(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        student.setId(id);
        students.put(id, student);
        return student;
    }
} 