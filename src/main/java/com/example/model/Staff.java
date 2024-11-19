package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Staff {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    @Column(name = "years_of_working")
    private int yearsOfWorking;

    @Column(nullable = false)
    private String role;

    @Column(name = "class_teacher")
    private boolean classTeacher;

    @Column(nullable = false)
    private double salary;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getYearsOfWorking() {
        return yearsOfWorking;
    }

    public void setYearsOfWorking(Integer yearsOfWorking) {
        this.yearsOfWorking = yearsOfWorking;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Boolean classTeacher) {
        this.classTeacher = classTeacher;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}

