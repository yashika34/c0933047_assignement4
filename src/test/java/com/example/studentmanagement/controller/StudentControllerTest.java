package com.example.studentmanagement.controller;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setValidator(new Validator() {}) // Ensure validation is applied
                .build();
    }

    @Test
    void testGetAllStudents_ShouldReturnStudentListView() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(
                new Student("John Doe", "john@example.com", 20),
                new Student("Alice Smith", "alice@example.com", 22)
        ));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attributeExists("students"));
    }

    @Test
    void testSaveStudent_ShouldRedirectOnSuccess() throws Exception {
        mockMvc.perform(post("/students/save")
                .param("name", "John Doe")
                .param("email", "john@example.com")
                .param("age", "20"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).addStudent(any(Student.class));
    }

    @Test
    void testSaveStudent_ShouldReturnFormOnValidationError() throws Exception {
        mockMvc.perform(post("/students/save")
                .param("name", "") // Invalid: name is required
                .param("email", "invalidemail") // Invalid email format
                .param("age", "15")) // Invalid: age must be ≥18
        .andExpect(status().isOk())
        .andExpect(view().name("new-student"))
        .andExpect(model().attributeHasFieldErrors("student", "name", "email", "age"));
    }

    @Test
    void testDeleteStudent_ShouldRedirectOnSuccess() throws Exception {
        int studentId = 1;

        mockMvc.perform(post("/students/delete/" + studentId))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/students"));

        verify(studentService, times(1)).deleteStudent(studentId);
    }
}
