package com.example.controller;

import com.example.model.Attendance;
import com.example.model.Student;
import com.example.repository.AttendanceRepository;
import com.example.repository.StudentRepository;
import com.example.service.AttendanceService;

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
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadAttendance(
            @RequestParam("file") MultipartFile file,
            HttpSession session) {
        try {
            // Get staff details from session
            String staffEmail = (String) session.getAttribute("username");
            
            if (staffEmail == null) {
                throw new Exception("Staff details not found in session.");
            }

            attendanceService.saveAttendanceFromCsv(file, staffEmail);
            return ResponseEntity.ok("Attendance uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/view")
    public ResponseEntity<?> viewAttendance(HttpSession session) {
        try {
            // Get staff email from session
            String staffEmail = (String) session.getAttribute("username");
            if (staffEmail == null) {
                throw new Exception("Staff email not found in session.");
            }

            // Fetch attendance records posted by this staff
            List<Attendance> attendanceList = attendanceService.getAttendanceByStaffEmail(staffEmail);
            return ResponseEntity.ok(attendanceList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/student")
    public ResponseEntity<?> getAttendanceByEmail(HttpSession session) {
        String studentEmail = (String) session.getAttribute("username"); // Get email from session

        if (studentEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Student is not logged in.");
        }

        // Fetch roll number using email
        Optional<Student> studentOptional = studentRepository.findByEmail(studentEmail);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        String rollNumber = studentOptional.get().getRollNumber();

        // Fetch attendance using roll number
        List<Attendance> attendanceRecords = attendanceRepository.findByStudentRollNo(rollNumber);

        if (attendanceRecords.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No attendance records found.");
        }

        // Calculate percentage
        long totalClasses = attendanceRecords.size();
        long attendedClasses = attendanceRecords.stream().filter(Attendance::isPresent).count();
        double attendancePercentage = (double) attendedClasses / totalClasses * 100;

        Map<String, Object> response = new HashMap<>();
        response.put("attendanceRecords", attendanceRecords);
        response.put("attendancePercentage", attendancePercentage);

        return ResponseEntity.ok(response);
    }
}