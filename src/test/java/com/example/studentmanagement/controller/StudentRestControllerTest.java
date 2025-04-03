package com.example.studentmanagement.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

        @MockBean
        private StudentService studentService;

        @Autowired
        private ObjectMapper objectMapper;

        private RequestStudentDTO requestStudentDTO;
        private ResponseStudentDTO responseStudentDTO;

        @BeforeEach
        void setUp() {
                requestStudentDTO = new RequestStudentDTO();
                requestStudentDTO.setName("John Doe");
                requestStudentDTO.setAge(20);

                responseStudentDTO = new ResponseStudentDTO();
                responseStudentDTO.setId(1L);
                responseStudentDTO.setName("John Doe");
                responseStudentDTO.setAge(20);
                responseStudentDTO.setResponseDate(LocalDateTime.now());
        }

        @Test
        void getAllStudents_ShouldReturnListOfStudents() throws Exception {
                // Arrange
                List<ResponseStudentDTO> students = Arrays.asList(responseStudentDTO);
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
                when(studentService.getStudentById(1L)).thenReturn(responseStudentDTO);

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
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.message").value("Student not found with id: 1"))
                                .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        void createStudent_ShouldCreateNewStudent() throws Exception {
                // Arrange
                when(studentService.createStudent(any(RequestStudentDTO.class))).thenReturn(responseStudentDTO);

                // Act & Assert
                mockMvc.perform(post("/api/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudentDTO)))
                                .andExpect(status().isCreated())
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
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void updateStudent_ShouldUpdateStudent() throws Exception {
                // Arrange
                when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class))).thenReturn(responseStudentDTO);

                // Act & Assert
                mockMvc.perform(put("/api/students/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudentDTO)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("John Doe"))
                                .andExpect(jsonPath("$.age").value(20));
        }

        @Test
        void updateStudent_ShouldReturnNotFound() throws Exception {
                // Arrange
                when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class)))
                                .thenThrow(new StudentNotFoundException("Student not found with id: 1"));

                // Act & Assert
                mockMvc.perform(put("/api/students/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestStudentDTO)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.message").value("Student not found with id: 1"))
                                .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        void deleteStudent_ShouldDeleteStudent() throws Exception {
                // Act & Assert
                mockMvc.perform(delete("/api/students/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        void deleteStudent_ShouldReturnNotFound() throws Exception {
                // Arrange
                doThrow(new StudentNotFoundException("Student not found with id: 1"))
                                .when(studentService).deleteStudent(1L);

                // Act & Assert
                mockMvc.perform(delete("/api/students/1"))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.message").value("Student not found with id: 1"))
                                .andExpect(jsonPath("$.timestamp").exists());
        }
}