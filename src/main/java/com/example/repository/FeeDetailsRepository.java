package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.FeeDetails;

@Repository
public interface FeeDetailsRepository extends JpaRepository<FeeDetails, Long> {
    Optional<FeeDetails> findByRollNo(String rollNo);
}