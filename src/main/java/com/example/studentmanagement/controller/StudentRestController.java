package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing students")
public class StudentRestController {
    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<List<ResponseStudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<ResponseStudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping
    @Operation(summary = "Create new student")
    public ResponseEntity<ResponseStudentDTO> createStudent(@Valid @RequestBody RequestStudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.createStudent(studentDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<ResponseStudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody RequestStudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
} 