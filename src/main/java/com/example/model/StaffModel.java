package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class StaffModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String qualification;

    @Column(nullable = false)
    private String yearsofWorking;

    @Column(nullable = false)
    private String doj;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getYearsofWorking() {
        return yearsofWorking;
    }

    public void setYearsofWorking(String yearsofWorking) {
        this.yearsofWorking = yearsofWorking;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StaffModel(Long id, String name, String age, String department, String email, String mobile,
            String qualification, String yearsofWorking, String doj, String salary, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.email = email;
        this.mobile = mobile;
        this.qualification = qualification;
        this.yearsofWorking = yearsofWorking;
        this.doj = doj;
        this.salary = salary;
        this.password = password;
    }

    public StaffModel() {}
    
}
