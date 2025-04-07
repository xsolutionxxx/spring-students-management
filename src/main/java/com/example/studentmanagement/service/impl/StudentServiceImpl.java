package com.example.studentmanagement.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    @Override
    public List<ResponseStudentDTO> getAllStudents() {
        logger.debug("Getting all students");
        List<ResponseStudentDTO> students = studentRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        logger.info("Successfully retrieved {} students", students.size());
        return students;
    }

    @Override
    public ResponseStudentDTO getStudentById(Long id) {
        logger.debug("Getting student with id: {}", id);
        return studentRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> {
                    logger.error("Student not found with id: {}", id);
                    return new StudentNotFoundException("Student not found with id: " + id);
                });
    }

    @Override
    @Transactional
    public ResponseStudentDTO createStudent(RequestStudentDTO studentDTO) {
        logger.debug("Creating new student: {}", studentDTO);
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        logger.info("Successfully created student with id: {}", savedStudent.getId());
        return convertToResponseDTO(savedStudent);
    }

    @Override
    @Transactional
    public ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO) {
        logger.debug("Updating student with id: {} and data: {}", id, studentDTO);
        if (!studentRepository.existsById(id)) {
            logger.error("Student not found with id: {}", id);
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        Student student = convertToEntity(studentDTO);
        student.setId(id);
        Student updatedStudent = studentRepository.save(student);
        logger.info("Successfully updated student with id: {}", id);
        return convertToResponseDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        logger.debug("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            logger.error("Student not found with id: {}", id);
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
        logger.info("Successfully deleted student with id: {}", id);
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
        dto.setResponseDate(student.getUpdatedAt());
        return dto;
    }
}