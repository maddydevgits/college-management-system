package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.model.StoreTeamModel;
import com.example.model.UserModel;
import com.example.repository.StoreTeamRepository;
import com.example.repository.UserRepository;
import com.example.service.StoreTeamService;

@Controller
@RequestMapping("/api/store")
public class StoreTeamController {

    @Autowired
    private StoreTeamService storeTeamService;

    @Autowired
    private StoreTeamRepository storeTeamRepository;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public String registerStoreMember(@ModelAttribute StoreTeamModel storeTeamModel, Model model) {
        String response = storeTeamService.addStoreMember(storeTeamModel);

        // Add the response message to the model
        model.addAttribute("responseMessage", response);

        // Return the same page to display the message
        return "registerInventoryTeam";
    }

    @GetMapping("/all")
    public ResponseEntity<List<StoreTeamModel>> getAllStaff() {
        List<StoreTeamModel> memberList = storeTeamRepository.findAll();
        return ResponseEntity.ok(memberList);
    }

    public StoreTeamModel getFById(Long id) {
        return storeTeamRepository.findById(id).orElse(null); // Return null if not found
    }

    // Delete a specific faculty by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id) {
        if (storeTeamRepository.existsById(id)) {
            
            StoreTeamModel fm=getFById(id);
            UserModel um=userRepo.findByEmail(fm.getEmail());
            userRepo.deleteById(um.getId());
            storeTeamRepository.deleteById(id);
            return ResponseEntity.ok("Store Member deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Store Member not found!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStaff(@PathVariable Long id, @RequestBody StoreTeamModel updatedFaculty) {
        // Check if the faculty exists
        if (!storeTeamRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Staff not found!");
        }

        // Fetch the existing faculty record
        StoreTeamModel existingFaculty = storeTeamRepository.findById(id).get();

        // Update the fields
        existingFaculty.setName(updatedFaculty.getName());
        existingFaculty.setAge(updatedFaculty.getAge());
        existingFaculty.setEmail(updatedFaculty.getEmail());
        existingFaculty.setMobile(updatedFaculty.getMobile());

        // Save the updated record
        storeTeamRepository.save(existingFaculty);

        return ResponseEntity.ok("Store Member updated successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreTeamModel> getFacultyById(@PathVariable Long id) {
        return storeTeamRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.status(404).body(null)); // Return 404 if not found
    }
}