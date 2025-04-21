package com.example.studentmanagement.config;

import com.example.studentmanagement.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        private final CustomUserDetailsService userDetailsService;

        public SecurityConfig(CustomUserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                new AntPathRequestMatcher("/"),
                                                                new AntPathRequestMatcher("/home"),
                                                                new AntPathRequestMatcher("/login"),
                                                                new AntPathRequestMatcher("/register"),
                                                                new AntPathRequestMatcher("/css/**"),
                                                                new AntPathRequestMatcher("/js/**"),
                                                                new AntPathRequestMatcher("/swagger-ui/**"),
                                                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                                                new AntPathRequestMatcher("/h2-console/**"),
                                                                new AntPathRequestMatcher("/access-denied"))
                                                .permitAll()
                                                .requestMatchers(
                                                                new AntPathRequestMatcher("/students"),
                                                                new AntPathRequestMatcher("/api/students"))
                                                .hasAnyRole("USER", "ADMIN")
                                                .requestMatchers(
                                                                new AntPathRequestMatcher("/students/**"),
                                                                new AntPathRequestMatcher("/api/students/**"))
                                                .hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/students")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutSuccessUrl("/")
                                                .permitAll())
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/access-denied"))
                                .headers(headers -> headers
                                                .frameOptions(frameOptions -> frameOptions.disable()));
                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}