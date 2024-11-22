package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.FeeDetails;
import com.example.model.Student;
import com.example.repository.FeeDetailsRepository;
import com.example.repository.StudentRepository;
import com.example.service.FeeDetailsService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/fees")
public class FeeDetailsController {

    @Autowired
    private FeeDetailsService feeDetailsService;

    @Autowired
    private FeeDetailsRepository feeDetailsRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFees(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        try {
            feeDetailsService.saveFeesFromCsv(file);
            return ResponseEntity.ok("Fees details uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading fees details: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<FeeDetails>> getAllFees() {
        List<FeeDetails> fees = feeDetailsRepository.findAll();
        if (fees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fees);
        }
        return ResponseEntity.ok(fees);
    }

    @GetMapping("/student-fee")
    public ResponseEntity<?> getStudentFeeDetails(HttpSession session) {
        // Get student email from session
        String studentEmail = (String) session.getAttribute("username");

        if (studentEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Student is not logged in.");
        }

        // Fetch student roll number using the email
        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        String rollNo = studentOptional.get().getRollNumber();

        // Fetch fee details using roll number
        Optional<FeeDetails> feeDetailsOptional = feeDetailsRepository.findByRollNo(rollNo);
        if (feeDetailsOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fee details not found for the student.");
        }

        return ResponseEntity.ok(feeDetailsOptional.get());
    }
}
