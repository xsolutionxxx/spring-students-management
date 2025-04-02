package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final StudentService studentService;

    @GetMapping("/home")
    public String home(Model model) {
        List<ResponseStudentDTO> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "home";
    }
} 