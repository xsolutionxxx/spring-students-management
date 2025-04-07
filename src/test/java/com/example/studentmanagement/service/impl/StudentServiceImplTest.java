package com.example.studentmanagement.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private RequestStudentDTO requestStudentDTO;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(20);

        requestStudentDTO = new RequestStudentDTO();
        requestStudentDTO.setName("John Doe");
        requestStudentDTO.setAge(20);
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        // Arrange
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<ResponseStudentDTO> result = studentService.getAllStudents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(student.getName(), result.get(0).getName());
        assertEquals(student.getAge(), result.get(0).getAge());
        verify(studentRepository).findAll();
    }

    @Test
    void getStudentById_ShouldReturnStudent() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Act
        ResponseStudentDTO result = studentService.getStudentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(student.getName(), result.getName());
        assertEquals(student.getAge(), result.getAge());
        verify(studentRepository).findById(1L);
    }

    @Test
    void getStudentById_ShouldThrowExceptionWhenStudentNotFound() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1L));
        verify(studentRepository).findById(1L);
    }

    @Test
    void createStudent_ShouldCreateNewStudent() {
        // Arrange
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        // Act
        ResponseStudentDTO result = studentService.createStudent(requestStudentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(student.getName(), result.getName());
        assertEquals(student.getAge(), result.getAge());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void updateStudent_ShouldUpdateExistingStudent() {
        // Arrange
        RequestStudentDTO updateDTO = new RequestStudentDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setAge(25);

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Updated Name");
        updatedStudent.setAge(25);

        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // Act
        ResponseStudentDTO result = studentService.updateStudent(1L, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedStudent.getName(), result.getName());
        assertEquals(updatedStudent.getAge(), result.getAge());
        verify(studentRepository).existsById(1L);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void updateStudent_ShouldThrowExceptionWhenStudentNotFound() {
        // Arrange
        RequestStudentDTO updateDTO = new RequestStudentDTO();
        updateDTO.setName("Updated Name");
        updateDTO.setAge(25);

        when(studentRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(1L, updateDTO));
        verify(studentRepository).existsById(1L);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void deleteStudent_ShouldDeleteStudent() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);

        // Act
        studentService.deleteStudent(1L);

        // Assert
        verify(studentRepository).existsById(1L);
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void deleteStudent_ShouldThrowExceptionWhenStudentNotFound() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(1L));
        verify(studentRepository).existsById(1L);
        verifyNoMoreInteractions(studentRepository);
    }
}