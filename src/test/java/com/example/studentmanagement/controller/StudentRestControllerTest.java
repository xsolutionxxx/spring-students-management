package com.example.studentmanagement.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private StudentService studentService;

        @Test
        void getAllStudents_ShouldReturnListOfStudents() throws Exception {
                // Arrange
                ResponseStudentDTO student = new ResponseStudentDTO(1L, "John Doe", 20, LocalDateTime.now());
                List<ResponseStudentDTO> students = Arrays.asList(student);
                when(studentService.getAllStudents()).thenReturn(students);

                // Act & Assert
                mockMvc.perform(get("/api/students"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].name").value("John Doe"))
                                .andExpect(jsonPath("$[0].age").value(20));
        }

        @Test
        void getStudentById_ShouldReturnStudent() throws Exception {
                // Arrange
                ResponseStudentDTO student = new ResponseStudentDTO(1L, "John Doe", 20, LocalDateTime.now());
                when(studentService.getStudentById(1L)).thenReturn(student);

                // Act & Assert
                mockMvc.perform(get("/api/students/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("John Doe"))
                                .andExpect(jsonPath("$.age").value(20));
        }

        @Test
        void getStudentById_ShouldReturnNotFound() throws Exception {
                // Arrange
                when(studentService.getStudentById(1L))
                                .thenThrow(new StudentNotFoundException("Student not found with id: 1"));

                // Act & Assert
                mockMvc.perform(get("/api/students/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Student not found with id: 1"));
        }

        @Test
        void createStudent_ShouldCreateNewStudent() throws Exception {
                // Arrange
                RequestStudentDTO requestStudent = new RequestStudentDTO();
                requestStudent.setName("John Doe");
                requestStudent.setAge(20);

                ResponseStudentDTO responseStudent = new ResponseStudentDTO(1L, "John Doe", 20, LocalDateTime.now());
                when(studentService.createStudent(any(RequestStudentDTO.class))).thenReturn(responseStudent);

                // Act & Assert
                mockMvc.perform(post("/api/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudent)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("John Doe"))
                                .andExpect(jsonPath("$.age").value(20));
        }

        @Test
        void createStudent_ShouldReturnBadRequestWhenInvalidData() throws Exception {
                // Arrange
                RequestStudentDTO requestStudent = new RequestStudentDTO();
                requestStudent.setName("J"); // Invalid name length
                requestStudent.setAge(15); // Invalid age

                // Act & Assert
                mockMvc.perform(post("/api/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudent)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void updateStudent_ShouldUpdateExistingStudent() throws Exception {
                // Arrange
                RequestStudentDTO requestStudent = new RequestStudentDTO();
                requestStudent.setName("John Doe Updated");
                requestStudent.setAge(21);

                ResponseStudentDTO responseStudent = new ResponseStudentDTO(1L, "John Doe Updated", 21,
                                LocalDateTime.now());
                when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class))).thenReturn(responseStudent);

                // Act & Assert
                mockMvc.perform(put("/api/students/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudent)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("John Doe Updated"))
                                .andExpect(jsonPath("$.age").value(21));
        }

        @Test
        void updateStudent_ShouldReturnNotFound() throws Exception {
                // Arrange
                RequestStudentDTO requestStudent = new RequestStudentDTO();
                requestStudent.setName("John Doe Updated");
                requestStudent.setAge(21);

                when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class)))
                                .thenThrow(new StudentNotFoundException("Student not found with id: 1"));

                // Act & Assert
                mockMvc.perform(put("/api/students/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudent)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Student not found with id: 1"));
        }

        @Test
        void deleteStudent_ShouldDeleteStudent() throws Exception {
                // Act & Assert
                mockMvc.perform(delete("/api/students/1"))
                                .andExpect(status().isOk());
        }

        @Test
        void deleteStudent_ShouldReturnNotFound() throws Exception {
                // Arrange
                doThrow(new StudentNotFoundException("Student not found with id: 1"))
                                .when(studentService).deleteStudent(1L);

                // Act & Assert
                mockMvc.perform(delete("/api/students/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Student not found with id: 1"));
        }
}