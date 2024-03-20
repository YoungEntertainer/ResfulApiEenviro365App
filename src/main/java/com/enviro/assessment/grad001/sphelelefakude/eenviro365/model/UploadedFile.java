package com.enviro.assessment.grad001.sphelelefakude.eenviro365.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String fileData;

    // Constructors
    public UploadedFile() {
    }

    public UploadedFile(String fileData) {
        this.fileData = fileData;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    // toString() method for debugging/logging
    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + id +
                ", fileData='" + fileData + '\'' +
                '}';
    }
}
