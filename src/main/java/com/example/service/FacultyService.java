package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.FacultyModel;
import com.example.model.UserModel;
import com.example.repository.FacultyRepository;
import com.example.repository.UserRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepo;

    public String addFaculty(FacultyModel facultyModel) {

        // Check for duplicates by email or mobile
        if (userRepo.existsByEmail(facultyModel.getEmail())) {
            return "Duplicate entry: Email already exists.";
        }
        if (facultyRepository.existsByEmail(facultyModel.getEmail())) {
            return "Duplicate entry: Email already exists.";
        }
        if (facultyRepository.existsByMobile(facultyModel.getMobile())) {
            return "Duplicate entry: Mobile number already exists.";
        }

        // Save the faculty data
        facultyRepository.save(facultyModel);
        UserModel user = new UserModel();
        user.setEmail(facultyModel.getEmail());
        user.setPassword(facultyModel.getPassword()); // Ideally, hash the password before saving
        user.setRole("faculty");
        userRepo.save(user);
        return "Faculty registered successfully!";
    }
}