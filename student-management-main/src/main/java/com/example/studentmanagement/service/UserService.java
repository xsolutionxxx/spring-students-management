package com.example.studentmanagement.service;

import com.example.studentmanagement.model.User;

public interface UserService {
    User registerUser(String username, String password);
}
