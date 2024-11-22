package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.FeeDetails;
import com.example.repository.FeeDetailsRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class FeeDetailsService {

    @Autowired
    private FeeDetailsRepository feeDetailsRepository;

    public void saveFeesFromCsv(MultipartFile file) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) { // Skip the header row
                    isHeader = false;
                    continue;
                }

                String[] columns = line.split(",");
                String rollNo = columns[0].trim();
                Double totalFees = Double.parseDouble(columns[1].trim());
                Double paidFees = Double.parseDouble(columns[2].trim());
                Double unpaidFees = Double.parseDouble(columns[3].trim());

                FeeDetails feeDetails = feeDetailsRepository.findByRollNo(rollNo)
                        .orElse(new FeeDetails());

                feeDetails.setRollNo(rollNo);
                feeDetails.setTotalFees(totalFees);
                feeDetails.setPaidFees(paidFees);
                feeDetails.setUnpaidFees(unpaidFees);

                feeDetailsRepository.save(feeDetails);
            }
        } catch (Exception e) {
            throw new Exception("Error processing CSV file: " + e.getMessage());
        }
    }
}