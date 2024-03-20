package com.enviro.assessment.grad001.sphelelefakude.eenviro365.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.sphelelefakude.eenviro365.model.UploadedFile;
import com.enviro.assessment.grad001.sphelelefakude.eenviro365.repository.FileRepository;

import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    // Method to process the uploaded file
    public UploadedFile processFile(UploadedFile UploadedFile) {
        // Implement file processing logic here (e.g., save/update data)
        return fileRepository.save(UploadedFile);
    }

    // Method to retrieve file data by ID
    public UploadedFile getFileById(Long id) {
        Optional<UploadedFile> optionalUploadedFile = fileRepository.findById(id);
        return optionalUploadedFile.orElse(null); // Return null if file not found
    }

    // Method to update file data
    public UploadedFile updateFile(UploadedFile UploadedFile) {
        // Check if the file exists
        Optional<UploadedFile> optionalExistingFile = fileRepository.findById(UploadedFile.getId());
        if (optionalExistingFile.isPresent()) {
            // File exists, update it
            UploadedFile existingFile = optionalExistingFile.get();
            existingFile.setFileData(UploadedFile.getFileData()); // Update file data
            return fileRepository.save(existingFile); // Save and return updated file
        } else {
            // File not found, return null or throw an exception
            return null; // You can throw a custom exception here if needed
        }
    }

    // Method to delete file by ID
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }
}
