package com.enviro.assessment.grad001.sphelelefakude.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.sphelelefakude.model.UploadedFile;
import com.enviro.assessment.grad001.sphelelefakude.service.FileService;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if file is empty
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            // Check if file size exceeds a limit (e.g., 10MB)
            if (file.getSize() > 10485760) { // 10 MB in bytes
                throw new IllegalArgumentException("File size exceeds the limit");
            }

            // Read file content
            String content = new String(file.getBytes());

            // Create UploadedFile object
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setFileData(content);

            // Process the file
            UploadedFile processedFile = fileService.processFile(uploadedFile);

            // Return success response
            return ResponseEntity.ok("File uploaded successfully with ID: " + processedFile.getId());
        } catch (IllegalArgumentException e) {
            // Handle specific validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + e.getMessage());
        } catch (IOException e) {
            // Handle file read error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<?> getProcessedResults(@PathVariable Long id) {
        try {
            // Attempt to retrieve the file data by ID
            try {
                UploadedFile fileData = fileService.getFileById(id);

                // If file data is found, return it with OK status
                if (fileData != null) {
                    return ResponseEntity.ok(fileData);
                } else {
                    // If file data is not found, return 404 Not Found status
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("File not found with ID: " + id);
                }
            } catch (Exception e) {
                // If an exception occurs during file retrieval, return internal server error
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to retrieve processed results: " + e.getMessage());
            }
        } catch (Exception ex) {
            // If an unexpected error occurs, return internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    // @PutMapping("/update/{id}")
    // public ResponseEntity<?> updateFile(@PathVariable Long id, @RequestBody
    // UploadedFile fileData) {
    // try {
    // fileData.setId(id); // Set ID from path variable
    // UploadedFile updatedFile = fileService.updateFile(fileData);
    // if (updatedFile != null) {
    // return ResponseEntity.ok("File updated successfully.");
    // } else {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body("File not found with ID: " + id);
    // }
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Failed to update file: " + e.getMessage());
    // }
    // }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<?> deleteFile(@PathVariable Long id) {
    // try {
    // fileService.deleteFile(id);
    // return ResponseEntity.ok("File deleted successfully.");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Failed to delete file: " + e.getMessage());
    // }
    // }
}
