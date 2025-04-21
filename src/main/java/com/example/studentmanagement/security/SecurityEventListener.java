package com.example.studentmanagement.security;

import com.example.studentmanagement.service.LoggingService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationEvent;

@Component
public class SecurityEventListener implements ApplicationListener<ApplicationEvent> {
    private final LoggingService loggingService;

    public SecurityEventListener(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
            String username = authEvent.getAuthentication().getName();
            loggingService.logUserLogin(username);
        } else if (event instanceof AbstractAuthenticationFailureEvent) {
            AbstractAuthenticationFailureEvent failureEvent = (AbstractAuthenticationFailureEvent) event;
            String username = failureEvent.getAuthentication().getName();
            String reason = failureEvent.getException().getMessage();
            loggingService.logLoginFailure(username, reason);
        } else if (event instanceof LogoutSuccessEvent) {
            LogoutSuccessEvent logoutEvent = (LogoutSuccessEvent) event;
            String username = logoutEvent.getAuthentication().getName();
            loggingService.logUserLogout(username);
        }
    }
}