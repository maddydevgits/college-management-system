package com.example.repository;

import com.example.model.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByRollNumber(String rollNumber); // Check for duplicate roll number
    boolean existsByEmail(String email); // Check for duplicate email
    Optional<Student> findByEmail(String email);
}