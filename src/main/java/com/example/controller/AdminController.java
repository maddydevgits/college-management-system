package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.UserModel;
import com.example.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepo;
	
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginpage(){
    	return "login";
    }

    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin_dashboard";
    }

    @GetMapping("/registerFaculty")
    public String register() {
    	return "registerFaculty";
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

    @GetMapping("/staffdetails")
    public String staffdetails(){
        return "staff-details";
    }

    @GetMapping("/registerStaff")
    public String registerStaff(){
        return "registerStaff";
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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate the session to log out the user
        return "redirect:/login";  // Redirect to the login page
    }

    @PostMapping("/loginForm")
    public String loginForm(HttpSession session,UserModel user,Model form) {
        
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Role: " + user.getRole());

        if(userRepo.existsByEmail(user.getEmail())==false){
            form.addAttribute("message", "Account doesn't exist, please contact admin");
            return "login";
        }

        UserModel dbuser = userRepo.findByEmail(user.getEmail());
        if (dbuser != null && dbuser.getPassword().equals(user.getPassword()) && dbuser.getRole().equals(user.getRole())) {
            // Store username in the session
            session.setAttribute("username", user.getEmail());
            if(dbuser.getRole().equals("staff"))
                return("redirect:/staffDashboard");
            if (dbuser.getRole().equals("store"))
                return ("redirect:/storeDashboard");
            if (dbuser.getRole().equals("student"))
                return ("redirect:/studentDashboard");
            if (dbuser.getRole().equals("faculty"))
                return ("redirect:/facultyDashboard");
            return ("redirect:/admin");
        } else {
            form.addAttribute("message", "Invalid details. Please try again.");
            return "login";  // Return to login page with an error message
        }
    }

    @GetMapping("/registerInventory")
    public String registerInventoryPage(){
        return "registerInventory";
    }

    @GetMapping("/registerInventoryTeam")
    public String registerInventoryTeamPage(){
        return "registerInventoryTeam";
    }

    @GetMapping("/storeDashboard")
    public String storeDashboardPage() {
        return "storeDashboard";
    }

    @GetMapping("/staffDashboard")
    public String staffDashboardPage(){
        return "staffDashboard";
    }

    @GetMapping("/studentDashboard")
    public String studentDashboardPage(){
        return "studentDashboard";
    }

    @GetMapping("/uploadProducts")
    public String uploadProductsPage(){
        return "uploadProducts";
    }

    @GetMapping("/uploadAttendance")
    public String uploadAttendancePage(){
        return "uploadAttendance";
    }

    @GetMapping("/uploadResult")
    public String uploadResultPage(){
        return "uploadResult";
    }

    @GetMapping("/viewAttendance")
    public String viewAttendancePage(){
        return "viewAttendance";
    }

    @GetMapping("/viewResult")
    public String viewResultPage(){
        return "viewResult";
    }

    @GetMapping("/viewProducts")
    public String viewProductsPage() {
        return "viewProducts";
    }

    @GetMapping("/viewProductsForStaff")
    public String viewProductsForStaffPage(){
        return "viewProductsForStaff";
    }

    @GetMapping("/registerStudent")
    public String registerStudentPage(){
        return "registerStudent";
    }

    @GetMapping("/viewProductsForStudent")
    public String viewProductsForStudentPage(){
        return "viewProductsForStudent";
    }

    @GetMapping("/facultyDashboard")
    public String facultyDashboardPage(){
        return "facultyDashboard";
    }

    @GetMapping("/viewProductsForFaculty")
    public String viewProductsForFacultyPage(){
        return "viewProductsForFaculty";
    }

    @GetMapping("/addMaterials")
    public String addMaterialsPage(){
        return "addMaterials";
    }

    @GetMapping("/viewMaterials")
    public String viewMaterialsPage(){
        return "viewMaterials";
    }

    @GetMapping("/uploadFees")
    public String uploadFees(){
        return "uploadFees";
    }

    @GetMapping("/viewFees")
    public String viewFeesPage(){
        return "viewFees";
    }

    @GetMapping("/systemReports")
    public String systemReportsPage(){
        return "systemReports";
    }
    
}
