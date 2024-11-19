package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.StaffModel;
import com.example.model.UserModel;
import com.example.repository.StaffRepository;
import com.example.repository.UserRepository;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserRepository userRepo;

    public String addStaff(StaffModel staffModel) {

        // Check for duplicates by email or mobile
        if (userRepo.existsByEmail(staffModel.getEmail())) {
            return "Duplicate entry: Email already exists.";
        }
        if (staffRepository.existsByEmail(staffModel.getEmail())) {
            return "Duplicate entry: Email already exists.";
        }
        if (staffRepository.existsByMobile(staffModel.getMobile())) {
            return "Duplicate entry: Mobile number already exists.";
        }

        // Save the faculty data
        staffRepository.save(staffModel);
        UserModel user = new UserModel();
        user.setEmail(staffModel.getEmail());
        user.setPassword(staffModel.getPassword()); // Ideally, hash the password before saving
        user.setRole("staff");
        userRepo.save(user);
        return "Staff registered successfully!";
    }
}