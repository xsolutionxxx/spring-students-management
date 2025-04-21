package com.example.studentmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logUserLogin(String username) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info("[{}] User '{}' logged in successfully", timestamp, username);
    }

    public void logUserLogout(String username) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info("[{}] User '{}' logged out", timestamp, username);
    }

    public void logUserRegistration(String username) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info("[{}] New user '{}' registered successfully", timestamp, username);
    }

    public void logLoginFailure(String username, String reason) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.warn("[{}] Failed login attempt for user '{}': {}", timestamp, username, reason);
    }
}