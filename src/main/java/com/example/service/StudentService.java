package com.example.service;

import com.example.model.Student;
import com.example.model.UserModel;
import com.example.repository.StudentRepository;
import com.example.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepo;

    public String registerStudent(Student student) {
        // Check for duplicate roll number
        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            return "Error: Roll Number already exists!";
        }

        // Check for duplicate email
        if (studentRepository.existsByEmail(student.getEmail())) {
            return "Error: Email already exists!";
        }

        studentRepository.save(student);
        UserModel user = new UserModel();
        user.setEmail(student.getEmail());
        user.setPassword(student.getPassword()); // Ideally, hash the password before saving
        user.setRole("student");
        userRepo.save(user);
        return "Student registered successfully!";
    }
}