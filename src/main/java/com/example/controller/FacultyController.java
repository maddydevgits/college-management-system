package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.model.FacultyModel;
import com.example.model.UserModel;
import com.example.repository.FacultyRepository;
import com.example.repository.UserRepository;
import com.example.service.FacultyService;

@Controller
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public String registerFaculty(@ModelAttribute FacultyModel facultyModel, Model model) {
        String response = facultyService.addFaculty(facultyModel);

        // Add the response message to the model
        model.addAttribute("responseMessage", response);

        // Return the same page to display the message
        return "registerFaculty";
    }

    @GetMapping("/all")
    public ResponseEntity<List<FacultyModel>> getAllFaculty() {
        List<FacultyModel> facultyList = facultyRepository.findAll();
        return ResponseEntity.ok(facultyList);
    }

    public FacultyModel getFById(Long id) {
        return facultyRepository.findById(id).orElse(null); // Return null if not found
    }

    // Delete a specific faculty by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        if (facultyRepository.existsById(id)) {
            
            FacultyModel fm=getFById(id);
            UserModel um=userRepo.findByEmail(fm.getEmail());
            userRepo.deleteById(um.getId());
            facultyRepository.deleteById(id);
            return ResponseEntity.ok("Faculty deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Faculty not found!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFaculty(@PathVariable Long id, @RequestBody FacultyModel updatedFaculty) {
        // Check if the faculty exists
        if (!facultyRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Faculty not found!");
        }

        // Fetch the existing faculty record
        FacultyModel existingFaculty = facultyRepository.findById(id).get();

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
        facultyRepository.save(existingFaculty);

        return ResponseEntity.ok("Faculty updated successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyModel> getFacultyById(@PathVariable Long id) {
        return facultyRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body(null)); // Return 404 if not found
    }
}