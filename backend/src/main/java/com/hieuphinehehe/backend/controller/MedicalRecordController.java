package com.hieuphinehehe.backend.controller;


import com.hieuphinehehe.backend.dto.response.MedicalRecordResponse;
import com.hieuphinehehe.backend.model.MedicalRecord;
import com.hieuphinehehe.backend.model.User;
import com.hieuphinehehe.backend.dto.request.medicalRecord.UpdateMedicalRecordRequest;
import com.hieuphinehehe.backend.dto.request.medicalRecord.AddMedicalRecordRequest;
import com.hieuphinehehe.backend.dto.response.ApiResponse;
import com.hieuphinehehe.backend.service.AuthenticationService;
import com.hieuphinehehe.backend.service.MedicalRecordService;
import com.hieuphinehehe.backend.utils.CustomPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/medical-records")
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordController {
    private final AuthenticationService authenticationService;
    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addMedicalRecord(
            @Valid @RequestBody AddMedicalRecordRequest addMedicalRecordRequest) {
        MedicalRecord medicalRecord = medicalRecordService.addMedicalRecord(addMedicalRecordRequest);
        ApiResponse<MedicalRecord> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Adding new medical record successfully",
                medicalRecord
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateMedicalRecord(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UpdateMedicalRecordRequest updateMedicalRecordRequest) {
        updateMedicalRecordRequest.setId(id);
        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(updateMedicalRecordRequest);
        ApiResponse<MedicalRecord> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Update medical record successfully",
                updatedMedicalRecord
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable("id") Integer id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getMedicalRecordById(@PathVariable("id") Integer id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordService.findMedicalRecordById(id);
        ApiResponse<Optional<MedicalRecord>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get medical record successfully",
                medicalRecord
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllMedicalRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Long memberId) {
        User user = authenticationService.getCurrentUser();
        CustomPagination<MedicalRecordResponse> medicalRecordsPage = medicalRecordService.getAllMedicalRecords(page,size,keyword,user.getId(), memberId);
        ApiResponse<CustomPagination<MedicalRecordResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get list of medical record successfully",
                medicalRecordsPage
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
