package com.enviro.assessment.grad001.sphelelefakude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviro.assessment.grad001.sphelelefakude.model.UploadedFile;

@Repository
public interface FileRepository extends JpaRepository<UploadedFile, Long> {
   
}
