package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<ResponseStudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseStudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    @Override
    public ResponseStudentDTO createStudent(RequestStudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        student = studentRepository.save(student);
        return convertToResponseDTO(student);
    }

    @Override
    public ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        student.setId(id);
        student = studentRepository.update(id, student);
        return convertToResponseDTO(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private Student convertToEntity(RequestStudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        return student;
    }

    private ResponseStudentDTO convertToResponseDTO(Student student) {
        ResponseStudentDTO dto = new ResponseStudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setAge(student.getAge());
        dto.setResponseDate(LocalDateTime.now());
        return dto;
    }
} 