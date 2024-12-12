package com.hieuphinehehe.backend.repository;

import com.hieuphinehehe.backend.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    void deleteByMedicalRecordId(Integer medicalRecordId);
}