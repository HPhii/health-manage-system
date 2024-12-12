package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.request.medicalRecord.AddMedicalRecordRequest;
import com.hieuphinehehe.backend.dto.request.medicalRecord.UpdateMedicalRecordRequest;
import com.hieuphinehehe.backend.dto.response.MedicalRecordResponse;
import com.hieuphinehehe.backend.utils.CustomPagination;
import com.hieuphinehehe.backend.model.MedicalRecord;

import java.util.Optional;

public interface MedicalRecordService {
    MedicalRecord addMedicalRecord(AddMedicalRecordRequest addMedicalRecordRequest);
    MedicalRecord updateMedicalRecord(UpdateMedicalRecordRequest updateMedicalRecordRequest);
    void deleteMedicalRecord(Integer medicalRecordID);
    CustomPagination<MedicalRecordResponse> getAllMedicalRecords(int page, int size, String keyword, Integer userID, Long memberId);
    Optional<MedicalRecord> findMedicalRecordById(Integer medicalRecordID);
}
