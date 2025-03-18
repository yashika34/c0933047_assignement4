package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    
    private final List<Student> students = new ArrayList<>();

    // Get all students
    public List<Student> getAllStudents() {
        return students;
    }

    // Add a new student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Delete student by ID
    public void deleteStudent(Integer id) {
        students.removeIf(student -> student.getId().equals(id));
    }
}
