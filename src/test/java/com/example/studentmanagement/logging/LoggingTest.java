package com.example.studentmanagement.logging;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.studentmanagement.config.TestSecurityConfig;
import com.example.studentmanagement.controller.StudentRestController;
import com.example.studentmanagement.dto.RequestStudentDTO;
import com.example.studentmanagement.dto.ResponseStudentDTO;
import com.example.studentmanagement.service.StudentService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@ExtendWith(MockitoExtension.class)
@Import(TestSecurityConfig.class)
class LoggingTest {

        @Mock
        private StudentService studentService;

        @InjectMocks
        private StudentRestController studentRestController;

        private MockMvc mockMvc;
        private ListAppender<ILoggingEvent> listAppender;
        private Logger logger;

        @BeforeEach
        void setUp() {
                mockMvc = MockMvcBuilders.standaloneSetup(studentRestController).build();

                // Налаштування тестового логера
                logger = (Logger) LoggerFactory.getLogger(StudentRestController.class);
                listAppender = new ListAppender<>();
                listAppender.start();
                logger.addAppender(listAppender);
        }

        @Test
        void testLoggingForGetAllStudents() throws Exception {
                // Arrange
                List<ResponseStudentDTO> students = Arrays.asList(
                                createResponseStudentDTO(1L, "John", 20),
                                createResponseStudentDTO(2L, "Jane", 22));
                when(studentService.getAllStudents()).thenReturn(students);

                // Act
                mockMvc.perform(get("/api/students"))
                                .andExpect(status().isOk());

                // Assert
                List<ILoggingEvent> logsList = listAppender.list;
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Received request to get all students")));
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage().contains("Returning")));
        }

        private ResponseStudentDTO createResponseStudentDTO(Long id, String name, Integer age) {
                ResponseStudentDTO dto = new ResponseStudentDTO();
                dto.setId(id);
                dto.setName(name);
                dto.setAge(age);
                dto.setUpdatedAt(LocalDateTime.now());
                return dto;
        }

        @Test
        void testLoggingForCreateStudent() throws Exception {
                // Arrange
                RequestStudentDTO requestStudent = new RequestStudentDTO();
                requestStudent.setName("John Doe");
                requestStudent.setAge(20);

                ResponseStudentDTO responseStudent = new ResponseStudentDTO();
                responseStudent.setId(1L);
                responseStudent.setName("John Doe");
                responseStudent.setAge(20);
                responseStudent.setUpdatedAt(LocalDateTime.now());

                when(studentService.createStudent(any(RequestStudentDTO.class)))
                                .thenReturn(responseStudent);

                // Act
                mockMvc.perform(post("/api/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"John Doe\",\"age\":20}"))
                                .andExpect(status().isCreated());

                // Assert
                List<ILoggingEvent> logsList = listAppender.list;
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Received request to create new student")));
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Successfully created student")));
        }

        @Test
        void testLoggingForUpdateStudent() throws Exception {
                // Arrange
                RequestStudentDTO requestStudent = new RequestStudentDTO();
                requestStudent.setName("John Doe Updated");
                requestStudent.setAge(21);

                ResponseStudentDTO responseStudent = new ResponseStudentDTO();
                responseStudent.setId(1L);
                responseStudent.setName("John Doe Updated");
                responseStudent.setAge(21);
                responseStudent.setUpdatedAt(LocalDateTime.now());

                when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class)))
                                .thenReturn(responseStudent);

                // Act
                mockMvc.perform(put("/api/students/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"John Doe Updated\",\"age\":21}"))
                                .andExpect(status().isOk());

                // Assert
                List<ILoggingEvent> logsList = listAppender.list;
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Received request to update student")));
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Successfully updated student")));
        }

        @Test
        void testLoggingForDeleteStudent() throws Exception {
                // Act
                mockMvc.perform(delete("/api/students/1"))
                                .andExpect(status().isNoContent());

                // Assert
                List<ILoggingEvent> logsList = listAppender.list;
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Received request to delete student")));
                assertTrue(logsList.stream()
                                .anyMatch(event -> event.getMessage()
                                                .contains("Successfully deleted student")));
        }
}