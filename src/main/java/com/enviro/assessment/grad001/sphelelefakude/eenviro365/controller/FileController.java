package com.enviro.assessment.grad001.sphelelefakude.eenviro365.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.sphelelefakude.eenviro365.model.UploadedFile;
import com.enviro.assessment.grad001.sphelelefakude.eenviro365.service.FileService;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;


   @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String content = new String(file.getBytes());
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setFileData(content);
            UploadedFile processedFile = fileService.processFile(uploadedFile);
            return ResponseEntity.ok("File uploaded successfully with ID: " + processedFile.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<?> getProcessedResults(@PathVariable Long id) {
        try {
            UploadedFile fileData = fileService.getFileById(id);
            if (fileData != null) {
                return ResponseEntity.ok(fileData);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("File not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to retrieve processed results: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFile(@PathVariable Long id, @RequestBody UploadedFile fileData) {
        try {
            fileData.setId(id); // Set ID from path variable
            UploadedFile updatedFile = fileService.updateFile(fileData);
            if (updatedFile != null) {
                return ResponseEntity.ok("File updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("File not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to update file: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to delete file: " + e.getMessage());
        }
    }
}
