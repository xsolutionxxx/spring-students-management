package com.example.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime responseDate;
} 