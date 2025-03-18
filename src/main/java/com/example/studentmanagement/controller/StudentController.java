package com.example.studentmanagement.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/students")


public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Show all students
    @GetMapping
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }

    // Show form to add new student
    @GetMapping("/new")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student("", "", 18));
        return "new-student";
    }

    // Save new student
    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "new-student";
        }
        studentService.addStudent(student);
        return "redirect:/students";
    }

    // Delete student by ID
    @DeleteMapping("/{id}")
public String deleteStudent(@PathVariable Integer id) {
    studentService.deleteStudent(id);
    return "redirect:/students";
}

    
    @GetMapping("/")
public String homeRedirect() {
    return "redirect:/students";
}

    // Export students as JSON
    @GetMapping("/json")
    @ResponseBody
    public List<Student> getStudentsJson() {
        return studentService.getAllStudents();
    }
}
