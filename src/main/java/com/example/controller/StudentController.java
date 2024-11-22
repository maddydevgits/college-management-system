package com.example.controller;

import com.example.model.StaffModel;
import com.example.model.Student;
import com.example.model.UserModel;
import com.example.repository.StudentRepository;
import com.example.repository.UserRepository;
import com.example.service.StudentService;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public ResponseEntity<?> getStudentDetails(HttpSession session) {
        // Retrieve email from session
        String studentEmail = (String) session.getAttribute("username");

        if (studentEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Student is not logged in.");
        }

        // Fetch student details using email
        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        return ResponseEntity.ok(studentOptional.get());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        String response = studentService.registerStudent(student);
        if (response.startsWith("Error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(updatedStudent.getName());
            student.setRollNumber(updatedStudent.getRollNumber());
            student.setBranch(updatedStudent.getBranch());
            student.setStudyingYear(updatedStudent.getStudyingYear());
            student.setMobile(updatedStudent.getMobile());
            student.setEmail(updatedStudent.getEmail());
            studentRepository.save(student);
            return ResponseEntity.ok("Student updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
    }

    public Student getFById(Long id) {
        return studentRepository.findById(id).orElse(null); // Return null if not found
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            Student fm=getFById(id);
            UserModel um=userRepo.findByEmail(fm.getEmail());
            userRepo.deleteById(um.getId());
            studentRepository.deleteById(id);
            return ResponseEntity.ok("Student deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateStudentProfile(HttpSession session, @RequestBody Map<String, String> updates) {
        String studentEmail = (String) session.getAttribute("username");

        if (studentEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Student is not logged in.");
        }

        // Fetch student by email
        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        // Update student details
        Student student = studentOptional.get();
        student.setEmail(updates.get("email"));
        student.setMobile(updates.get("mobile"));
        studentRepository.save(student);

        return ResponseEntity.ok("Profile updated successfully!");
    }

    @GetMapping("/details")
    public ResponseEntity<?> getStudentDetails1(HttpSession session) {
        String studentEmail = (String) session.getAttribute("username");

        if (studentEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Student is not logged in.");
        }

        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);

        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        Student student = studentOptional.get();

        Map<String, Object> response = new HashMap<>();
        response.put("branch", student.getBranch());
        response.put("year", student.getStudyingYear());

        return ResponseEntity.ok(response);
    }
}