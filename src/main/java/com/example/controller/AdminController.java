package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/loginpage")
    public String loginpage(){
    	return "login";
    	
    }
    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin_dashboard";
    }
    @GetMapping("/profregister")
    public String register() {
    	return "prof-register";
    }
    @GetMapping("/studnetregister")
    public String studentregister() {
    	return "student-register";
    }
    @GetMapping("/studentdetails")
    public String studentdetails() {
    	return "student-details";
    }
    @GetMapping("/staffregiser")
    public String staffregister() {
    	return "staff-register";
    }
    
    @GetMapping("/employeedetails")
    public String employeedetails() {
    	return "employee-details";
    }
    @GetMapping("/staffdashboard")
    public String staffdachboard() {
    	return "staff-dashboard";
    }
    @GetMapping("/professordashboard")
    public String professordashboard() {
    	return "professor_dashboard";
    }
    @GetMapping("/attendance")
    public String attendance() {
    	return "attendance";
    }
    @GetMapping("/addresults")
    public String results() {
    	return "add-results";
    }
    
    @GetMapping("/addcourse")
    public String addcourse() {
    	return "add-course";
    }
    @GetMapping("/prof-student")
    public String Profstudent(){
    	return "prof-student details";
    }
    @GetMapping("/studentdashboard")
    public String studentdashboard() {
    	return "student_dashboard";
    }
    @GetMapping("/courses")
    public String courses() {
    	return "courses";
    }
}
