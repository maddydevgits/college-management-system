package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Material;
import com.example.repository.MaterialRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/materials")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    @PostMapping
    public ResponseEntity<?> addMaterial(@RequestBody Material material, HttpSession session) {
        // Get faculty email from session
        String facultyEmail = (String) session.getAttribute("username");

        if (facultyEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Faculty is not logged in.");
        }

        // Set additional fields
        material.setUploadedBy(facultyEmail);

        // Save material
        materialRepository.save(material);

        return ResponseEntity.ok("Material added successfully!");
    }

    @GetMapping
    public ResponseEntity<?> getMaterials(@RequestParam String branch, @RequestParam int year) {
        List<Material> materials = materialRepository.findByBranchAndYear(branch, year);

        if (materials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No materials found.");
        }

        return ResponseEntity.ok(materials);
    }

    @GetMapping("/my-materials")
    public ResponseEntity<?> getMaterialsByFaculty(HttpSession session) {
        // Get faculty email from session
        String facultyEmail = (String) session.getAttribute("username");

        if (facultyEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Faculty is not logged in.");
        }

        // Fetch materials uploaded by the faculty
        List<Material> materials = materialRepository.findByUploadedBy(facultyEmail);

        if (materials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No materials found.");
        }

        return ResponseEntity.ok(materials);
    }

    @GetMapping("/student-materials")
    public ResponseEntity<?> getMaterialsForStudent(@RequestParam String branch, @RequestParam int year) {
        List<Material> materials = materialRepository.findByBranchAndYear(branch, year);

        if (materials.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No materials found for your branch and year.");
        }

        return ResponseEntity.ok(materials);
    }
}