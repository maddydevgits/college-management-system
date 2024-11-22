package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByBranchAndYear(String branch, int year);
    List<Material> findByUploadedBy(String uploadedBy);
}