package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.RegisterUserDTO;
import com.example.studentmanagement.dto.UserRoleDTO;
import com.example.studentmanagement.model.User;
import com.example.studentmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggingService loggingService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggingService loggingService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loggingService = loggingService;
    }

    public User registerUser(RegisterUserDTO registerUserDTO) {
        logger.debug("Starting user registration process for username: {}", registerUserDTO.getUsername());

        if (userRepository.existsByUsername(registerUserDTO.getUsername())) {
            logger.warn("Registration failed: Username {} already exists", registerUserDTO.getUsername());
            loggingService.logLoginFailure(registerUserDTO.getUsername(), "Username already exists");
            throw new RuntimeException("Username already exists");
        }

        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            logger.warn("Registration failed: Passwords do not match for username: {}", registerUserDTO.getUsername());
            loggingService.logLoginFailure(registerUserDTO.getUsername(), "Passwords do not match");
            throw new RuntimeException("Passwords do not match");
        }

        User user = new User();
        user.setUsername(registerUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);

        logger.debug("Attempting to save user: {}", user.getUsername());
        User savedUser = userRepository.save(user);
        logger.info("User successfully saved with ID: {}", savedUser.getId());

        loggingService.logUserRegistration(savedUser.getUsername());
        return savedUser;
    }

    public List<UserRoleDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserRoleDTO)
                .collect(Collectors.toList());
    }

    public UserRoleDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToUserRoleDTO(user);
    }

    public UserRoleDTO updateUserRoles(Long userId, Set<String> newRoles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRoles(newRoles);
        User updatedUser = userRepository.save(user);
        logger.info("Updated roles for user {}: {}", updatedUser.getUsername(), newRoles);

        return convertToUserRoleDTO(updatedUser);
    }

    private UserRoleDTO convertToUserRoleDTO(User user) {
        UserRoleDTO dto = new UserRoleDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
    }
}