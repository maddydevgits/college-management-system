package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.StoreTeamModel;
import com.example.model.UserModel;
import com.example.repository.StoreTeamRepository;
import com.example.repository.UserRepository;

@Service
public class StoreTeamService {

    @Autowired
    private StoreTeamRepository storeTeamRepository;

    @Autowired
    private UserRepository userRepo;

    public String addStoreMember(StoreTeamModel storeTeamModel) {

        // Check for duplicates by email or mobile
        if (userRepo.existsByEmail(storeTeamModel.getEmail())) {
            return "Duplicate entry: Email already exists.";
        }
        if (storeTeamRepository.existsByEmail(storeTeamModel.getEmail())) {
            return "Duplicate entry: Email already exists.";
        }
        if (storeTeamRepository.existsByMobile(storeTeamModel.getMobile())) {
            return "Duplicate entry: Mobile number already exists.";
        }

        // Save the faculty data
        storeTeamRepository.save(storeTeamModel);
        UserModel user = new UserModel();
        user.setEmail(storeTeamModel.getEmail());
        user.setPassword(storeTeamModel.getPassword()); // Ideally, hash the password before saving
        user.setRole("store");
        userRepo.save(user);
        return "Store Member registered successfully!";
    }
}