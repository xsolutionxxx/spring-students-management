package com.example.studentmanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.service.StudentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String showStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String addStudent(@ModelAttribute RequestStudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
        return "redirect:/students";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}