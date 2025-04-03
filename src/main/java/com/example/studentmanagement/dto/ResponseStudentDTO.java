package com.example.studentmanagement.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseStudentDTO {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime responseDate;
}