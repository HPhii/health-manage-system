package com.hieuphinehehe.backend.repository;

import com.hieuphinehehe.backend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer>{
    void deleteByMedicalRecordId(Integer medicalRecordId);
}
