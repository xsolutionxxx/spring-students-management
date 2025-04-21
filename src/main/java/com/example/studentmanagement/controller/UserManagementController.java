package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.UserRoleDTO;
import com.example.studentmanagement.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {
    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit-user";
    }

    @PostMapping("/{id}/roles")
    public String updateUserRoles(@PathVariable Long id, @RequestParam Set<String> roles) {
        userService.updateUserRoles(id, roles);
        return "redirect:/admin/users";
    }
}