package com.example.studentmanagement.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.studentmanagement.model.Student;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(); // Initialize service before each test
    }

    @Test
    void testAddStudent_ShouldIncreaseListSize() {
        // Arrange
        Student student = new Student("John Doe", "john@example.com", 20);

        // Act
        studentService.addStudent(student);
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertEquals(1, students.size(), "Student list size should be 1 after adding");
    }

    @Test
    void testGetAllStudents_ShouldReturnEmptyListInitially() {
        // Act
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertTrue(students.isEmpty(), "Student list should be empty initially");
    }

    @Test
    void testDeleteStudent_ShouldRemoveStudentFromList() {
        // Arrange
        Student student = new Student("Alice Smith", "alice@example.com", 22);
        studentService.addStudent(student);
        int id = student.getId(); // Capture the generated ID

        // Act
        studentService.deleteStudent(id);
        List<Student> students = studentService.getAllStudents();

        // Assert
        assertEquals(0, students.size(), "Student list should be empty after deletion");
    }

    @Test
    void testDeleteNonExistingStudent_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> studentService.deleteStudent(999), "Deleting a non-existing student should not throw an exception");
    }
}
