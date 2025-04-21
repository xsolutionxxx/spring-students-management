package com.example.studentmanagement.service;

import java.util.List;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;

public interface StudentService {
    List<ResponseStudentDTO> getAllStudents();

    ResponseStudentDTO getStudentById(Long id);

    ResponseStudentDTO createStudent(RequestStudentDTO studentDTO);

    ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO);

    void deleteStudent(Long id);
}