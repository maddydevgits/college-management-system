package com.example.repository;

import com.example.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByStudentRollNoAndSubjectNameAndSemesterAndYear(
        String studentRollNo,
        String subjectName,
        String semester,
        String year
    );
    List<Result> findByPostedBy(String postedBy); // Fetch results by postedBy email
    List<Result> findByStudentRollNo(String rollNumber);
}