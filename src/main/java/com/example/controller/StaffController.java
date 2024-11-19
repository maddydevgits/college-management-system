package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.model.StaffModel;
import com.example.model.UserModel;
import com.example.repository.StaffRepository;
import com.example.repository.UserRepository;
import com.example.service.StaffService;

@Controller
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public String registerFaculty(@ModelAttribute StaffModel staffModel, Model model) {
        String response = staffService.addStaff(staffModel);

        // Add the response message to the model
        model.addAttribute("responseMessage", response);

        // Return the same page to display the message
        return "registerStaff";
    }

    @GetMapping("/all")
    public ResponseEntity<List<StaffModel>> getAllStaff() {
        List<StaffModel> facultyList = staffRepository.findAll();
        return ResponseEntity.ok(facultyList);
    }

    public StaffModel getFById(Long id) {
        return staffRepository.findById(id).orElse(null); // Return null if not found
    }

    // Delete a specific faculty by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        if (staffRepository.existsById(id)) {
            
            StaffModel fm=getFById(id);
            UserModel um=userRepo.findByEmail(fm.getEmail());
            userRepo.deleteById(um.getId());
            staffRepository.deleteById(id);
            return ResponseEntity.ok("Staff deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Staff not found!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable Long id, @RequestBody StaffModel updatedFaculty) {
        // Check if the faculty exists
        if (!staffRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Staff not found!");
        }

        // Fetch the existing faculty record
        StaffModel existingFaculty = staffRepository.findById(id).get();

        // Update the fields
        existingFaculty.setName(updatedFaculty.getName());
        existingFaculty.setAge(updatedFaculty.getAge());
        existingFaculty.setDepartment(updatedFaculty.getDepartment());
        existingFaculty.setEmail(updatedFaculty.getEmail());
        existingFaculty.setMobile(updatedFaculty.getMobile());
        existingFaculty.setQualification(updatedFaculty.getQualification());
        existingFaculty.setYearsofWorking(updatedFaculty.getYearsofWorking());
        existingFaculty.setDoj(updatedFaculty.getDoj());
        existingFaculty.setSalary(updatedFaculty.getSalary());

        // Save the updated record
        staffRepository.save(existingFaculty);

        return ResponseEntity.ok("Staff updated successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffModel> getFacultyById(@PathVariable Long id) {
        return staffRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body(null)); // Return 404 if not found
    }
}