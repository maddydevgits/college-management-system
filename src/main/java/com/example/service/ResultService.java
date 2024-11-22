package com.example.service;

import com.example.model.Result;
import com.example.repository.ResultRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    public void saveResultsFromCsv(MultipartFile file, String postedBy) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

            for (CSVRecord record : records) {
                String studentRollNo = record.get("Roll No");
                String subjectName = record.get("Subject Name");
                String semester = record.get("Semester");
                String year = record.get("Year");

                // Check for an existing record
                Optional<Result> existingResult = resultRepository.findByStudentRollNoAndSubjectNameAndSemesterAndYear(
                    studentRollNo, subjectName, semester, year
                );

                if (existingResult.isPresent()) {
                    // Update the existing record
                    Result result = existingResult.get();
                    result.setMarks(Integer.parseInt(record.get("Marks"))); // Update marks
                    result.setPostedBy(postedBy); // Update postedBy
                    resultRepository.save(result);
                } else {
                    // Insert a new record
                    Result newResult = new Result();
                    newResult.setStudentName(record.get("Student Name"));
                    newResult.setStudentRollNo(studentRollNo);
                    newResult.setSubjectName(subjectName);
                    newResult.setMarks(Integer.parseInt(record.get("Marks")));
                    newResult.setSemester(semester);
                    newResult.setYear(year);
                    newResult.setPostedBy(postedBy);

                    resultRepository.save(newResult);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error processing CSV file: " + e.getMessage());
        }
    }

    public List<Result> getResultsByPostedBy(String postedBy) {
        return resultRepository.findByPostedBy(postedBy);
    }
}