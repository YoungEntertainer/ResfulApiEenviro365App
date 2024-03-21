package com.enviro.assessment.grad001.sphelelefakude.eenviro365.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviro.assessment.grad001.sphelelefakude.eenviro365.model.UploadedFile;

@Repository
public interface FileRepository extends JpaRepository<UploadedFile, Long> {
   
}
