package com.example.studentmanagement.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Student {
    
    private static int idCounter = 1; // ID auto-generation
    
    private Integer id;
    
    @NotNull(message = "Name is required")
    @Size(min = 2, max = 40, message = "Name must be between 2 and 40 characters")
    private String name;
    
    @NotNull(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;
    
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    // Constructor
    public Student(String name, String email, Integer age) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
