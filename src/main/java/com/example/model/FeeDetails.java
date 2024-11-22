package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name="feedetails")
public class FeeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rollNo;
    private Double totalFees;
    private Double paidFees;
    private Double unpaidFees;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRollNo() {
        return rollNo;
    }
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }
    public Double getTotalFees() {
        return totalFees;
    }
    public void setTotalFees(Double totalFees) {
        this.totalFees = totalFees;
    }
    public Double getPaidFees() {
        return paidFees;
    }
    public void setPaidFees(Double paidFees) {
        this.paidFees = paidFees;
    }
    public Double getUnpaidFees() {
        return unpaidFees;
    }
    public void setUnpaidFees(Double unpaidFees) {
        this.unpaidFees = unpaidFees;
    }

    public FeeDetails(Long id, String rollNo, Double totalFees, Double paidFees, Double unpaidFees) {
        this.id = id;
        this.rollNo = rollNo;
        this.totalFees = totalFees;
        this.paidFees = paidFees;
        this.unpaidFees = unpaidFees;
    }

    public FeeDetails() {}
    
}