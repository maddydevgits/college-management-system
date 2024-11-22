package com.example.repository;

import com.example.model.Attendance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByPostedBy(String postedByEmail); 
    List<Attendance> findByStudentRollNo(String rollNumber);
}