package com.example.studentmanagement.dto;

import java.util.Set;

public class UserRoleDTO {
    private Long userId;
    private String username;
    private Set<String> roles;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}