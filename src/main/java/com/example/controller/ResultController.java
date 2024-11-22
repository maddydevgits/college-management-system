package com.example.controller;

import com.example.model.Result;
import com.example.model.Student;
import com.example.repository.ResultRepository;
import com.example.repository.StudentRepository;
import com.example.service.ResultService;

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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ResultRepository resultRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResults(
            @RequestParam("file") MultipartFile file,
            HttpSession session) {
        try {
            // Retrieve the staff email (postedBy) from the session
            String postedBy = (String) session.getAttribute("username");
            if (postedBy == null) {
                throw new Exception("Staff email not found in session.");
            }

            resultService.saveResultsFromCsv(file, postedBy);
            return ResponseEntity.ok("Results uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Endpoint to view results uploaded by the staff
    @GetMapping("/view")
    public ResponseEntity<?> viewResults(HttpSession session) {
        try {
            // Get staff email (postedBy) from session
            String postedBy = (String) session.getAttribute("username");
            if (postedBy == null) {
                throw new Exception("Staff email not found in session.");
            }

            // Fetch results uploaded by this staff
            List<Result> results = resultService.getResultsByPostedBy(postedBy);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/student")
    public ResponseEntity<?> getResultsForStudent(HttpSession session) {
        // Get the student's email from the session
        String studentEmail = (String) session.getAttribute("username");

        if (studentEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Student is not logged in.");
        }

        // Fetch the roll number using the student's email
        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }

        String rollNumber = studentOptional.get().getRollNumber();

        // Fetch results by roll number
        List<Result> results = resultRepository.findByStudentRollNo(rollNumber);

        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No results found for the student.");
        }

        // Calculate aggregate marks percentage and CGPA
        int totalMarksObtained = results.stream().mapToInt(Result::getMarks).sum();
        int totalSubjects = results.size();
        int totalMaximumMarks = totalSubjects * 100; // Assuming each subject has a max of 100 marks
        double aggregatePercentage = (double) totalMarksObtained / totalMaximumMarks * 100;
        double cgpa = aggregatePercentage / 10;

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("results", results);
        response.put("aggregatePercentage", aggregatePercentage);
        response.put("cgpa", cgpa);

        return ResponseEntity.ok(response);
    }
}
    