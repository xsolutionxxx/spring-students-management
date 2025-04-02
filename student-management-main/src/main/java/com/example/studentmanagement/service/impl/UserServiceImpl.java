package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.model.Role;
import com.example.studentmanagement.model.User;
import com.example.studentmanagement.repository.RoleRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // Призначаємо роль "USER" (якщо ще немає)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }
}
