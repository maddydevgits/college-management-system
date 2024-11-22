package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.model.StoreTeamModel;



@Repository
public interface StoreTeamRepository extends JpaRepository<StoreTeamModel, Long> {
    // Method to check for duplicates by email or mobile
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
}
