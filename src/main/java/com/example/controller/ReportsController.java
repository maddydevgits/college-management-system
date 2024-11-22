package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Attendance;
import com.example.model.FeeDetails;
import com.example.model.Student;
import com.example.repository.AttendanceRepository;
import com.example.repository.FeeDetailsRepository;
import com.example.repository.StudentRepository;

@Controller
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private FeeDetailsRepository feeDetailsRepository;

    @GetMapping("/attendance-report")
    public ResponseEntity<List<Map<String, Object>>> generateAttendanceReport() {
        List<Map<String, Object>> report = new ArrayList<>();
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            List<Attendance> attendanceRecords = attendanceRepository.findByStudentRollNo(student.getRollNumber());
            long totalClasses = attendanceRecords.size();
            long attendedClasses = attendanceRecords.stream().filter(Attendance::isPresent).count();
            double attendancePercentage = totalClasses > 0 ? ((double) attendedClasses / totalClasses * 100) : 0.0;

            Map<String, Object> studentReport = new HashMap<>();
            studentReport.put("name", student.getName());
            studentReport.put("rollNo", student.getRollNumber());
            studentReport.put("branch", student.getBranch());
            studentReport.put("attendancePercentage", attendancePercentage);

            report.add(studentReport);
        }

        return ResponseEntity.ok(report);
    }

    @GetMapping("/fees-report")
    public ResponseEntity<List<Map<String, Object>>> generateFeesReport() {
        List<FeeDetails> feeDetailsList = feeDetailsRepository.findAll();
        List<Map<String, Object>> report = feeDetailsList.stream().map(fee -> {
            Map<String, Object> studentReport = new HashMap<>();
            studentReport.put("rollNo", fee.getRollNo());
            studentReport.put("totalFees", fee.getTotalFees());
            studentReport.put("paidFees", fee.getPaidFees());
            studentReport.put("unpaidFees", fee.getUnpaidFees());
            return studentReport;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(report);
    }
    
}
