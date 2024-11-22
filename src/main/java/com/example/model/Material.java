package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name="materials")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String materialName;
    private String branch;
    private int year;
    private String googleDriveLink;
    private String uploadedBy; // Faculty email
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    public String getBranch() {
        return branch;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getGoogleDriveLink() {
        return googleDriveLink;
    }
    public void setGoogleDriveLink(String googleDriveLink) {
        this.googleDriveLink = googleDriveLink;
    }
    public String getUploadedBy() {
        return uploadedBy;
    }
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    public Material(Long id, String materialName, String branch, int year, String googleDriveLink, String uploadedBy) {
        this.id = id;
        this.materialName = materialName;
        this.branch = branch;
        this.year = year;
        this.googleDriveLink = googleDriveLink;
        this.uploadedBy = uploadedBy;
    }

    public Material(){}
}