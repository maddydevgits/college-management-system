package com.example.service;

import com.example.model.Attendance;
import com.example.repository.AttendanceRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void saveAttendanceFromCsv(MultipartFile file, String staffEmail) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

            for (CSVRecord record : records) {
                Attendance attendance = new Attendance();
                attendance.setStudentName(record.get("Student Name"));
                attendance.setStudentRollNo(record.get("Roll No"));
                attendance.setDate(record.get("Date"));
                attendance.setPresent(Boolean.parseBoolean(record.get("Present")));
                attendance.setPostedBy(staffEmail); // Set the staff's email

                attendanceRepository.save(attendance);
            }
        } catch (Exception e) {
            throw new Exception("Error processing CSV file: " + e.getMessage());
        }
    }

    public List<Attendance> getAttendanceByStaffEmail(String staffEmail) {
        return attendanceRepository.findByPostedBy(staffEmail);
    }
}