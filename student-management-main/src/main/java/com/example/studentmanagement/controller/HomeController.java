package com.example.studentmanagement.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StudentService studentService;

    @GetMapping("/home")
    public String homePage(Model model) {
        // Отримуємо список студентів
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        // Отримуємо поточного авторизованого користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username);

        // Перевіряємо, чи є у користувача роль "Admin"
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "home"; // Назва HTML-файлу в папці templates (home.html)
    }
}
