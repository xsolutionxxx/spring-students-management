package com.example.studentmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentRestController {
    private static final Logger logger = LoggerFactory.getLogger(StudentRestController.class);
    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<List<ResponseStudentDTO>> getAllStudents() {
        logger.debug("Received request to get all students");
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        logger.info("Returning {} students", students.size());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<ResponseStudentDTO> getStudentById(@PathVariable Long id) {
        logger.debug("Received request to get student with id: {}", id);
        ResponseStudentDTO student = studentService.getStudentById(id);
        logger.info("Returning student with id: {}", id);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @Operation(summary = "Create new student")
    public ResponseEntity<ResponseStudentDTO> createStudent(@Valid @RequestBody RequestStudentDTO studentDTO) {
        logger.debug("Received request to create new student: {}", studentDTO);
        ResponseStudentDTO createdStudent = studentService.createStudent(studentDTO);
        logger.info("Successfully created student with id: {}", createdStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<ResponseStudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody RequestStudentDTO studentDTO) {
        logger.debug("Received request to update student with id: {} and data: {}", id, studentDTO);
        ResponseStudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        logger.info("Successfully updated student with id: {}", id);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        logger.debug("Received request to delete student with id: {}", id);
        studentService.deleteStudent(id);
        logger.info("Successfully deleted student with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}