package com.hieuphinehehe.backend.service.impl;

import com.hieuphinehehe.backend.dto.request.medicalRecord.AddMedicalRecordRequest;
import com.hieuphinehehe.backend.dto.request.medicalRecord.DocumentRequest;
import com.hieuphinehehe.backend.dto.request.medicalRecord.MedicationRequest;
import com.hieuphinehehe.backend.dto.request.medicalRecord.UpdateMedicalRecordRequest;
import com.hieuphinehehe.backend.dto.response.MedicalRecordResponse;
import com.hieuphinehehe.backend.mapper.MedicalRecordMapper;
import com.hieuphinehehe.backend.model.Document;
import com.hieuphinehehe.backend.model.MedicalRecord;
import com.hieuphinehehe.backend.model.Medication;
import com.hieuphinehehe.backend.model.Member;
import com.hieuphinehehe.backend.repository.DocumentRepository;
import com.hieuphinehehe.backend.repository.MedicalRecordRepository;
import com.hieuphinehehe.backend.repository.MedicationRepository;
import com.hieuphinehehe.backend.service.MedicalRecordService;
import com.hieuphinehehe.backend.service.MemberService;
import com.hieuphinehehe.backend.utils.CustomPagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicationRepository medicationRepository;
    private final DocumentRepository documentRepository;
    private final MemberService memberService;

    @Override
    public MedicalRecord addMedicalRecord(AddMedicalRecordRequest addMedicalRecordRequest) {
        Member member = memberService.getMemberById(addMedicalRecordRequest.getMemberId());
        MedicalRecord medicalRecord = MedicalRecord.builder()
                .member(member)
                .date(addMedicalRecordRequest.getDate())
                .doctor(addMedicalRecordRequest.getDoctor())
                .symptoms(addMedicalRecordRequest.getSymptoms())
                .diagnosis(addMedicalRecordRequest.getDiagnosis())
                .treatment(addMedicalRecordRequest.getTreatment())
                .facilityName(addMedicalRecordRequest.getFacilityName())
                .build();
        medicalRecord = medicalRecordRepository.save(medicalRecord);
        for(MedicationRequest medicationRequest : addMedicalRecordRequest.getMedications()){
            Medication medication = Medication.builder()
                    .medicalRecord(medicalRecord)
                    .name(medicationRequest.getName())
                    .startDate(medicationRequest.getStartDate())
                    .endDate(medicationRequest.getEndDate())
                    .position(medicationRequest.getPosition())
                    .frequency(medicationRequest.getFrequency())
                    .build();
            medicationRepository.save(medication);
        }
        for (DocumentRequest documentRequest : addMedicalRecordRequest.getDocuments()){
            Document document = Document.builder()
                    .medicalRecord(medicalRecord)
                    .name(documentRequest.getName())
                    .position(documentRequest.getPosition())
                    .size(documentRequest.getSize())
                    .type(documentRequest.getType())
                    .path(documentRequest.getPath())
                    .build();
            documentRepository.save(document);
        }
        return medicalRecord;
    }

    @Override
    @Transactional
    public MedicalRecord updateMedicalRecord(UpdateMedicalRecordRequest updateMedicalRecordRequest) {
        MedicalRecord existingRecord = medicalRecordRepository.findById(updateMedicalRecordRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Medical Record not found"));
        existingRecord.setDate(updateMedicalRecordRequest.getDate());
        existingRecord.setDoctor(updateMedicalRecordRequest.getDoctor());
        existingRecord.setSymptoms(updateMedicalRecordRequest.getSymptoms());
        existingRecord.setDiagnosis(updateMedicalRecordRequest.getDiagnosis());
        existingRecord.setTreatment(updateMedicalRecordRequest.getTreatment());
        existingRecord.setFacilityName(updateMedicalRecordRequest.getFacilityName());
        medicalRecordRepository.save(existingRecord);

        medicationRepository.deleteByMedicalRecordId(existingRecord.getId());
        documentRepository.deleteByMedicalRecordId(existingRecord.getId());

        for (MedicationRequest medicationRequest : updateMedicalRecordRequest.getMedications()) {
            Medication medication = Medication.builder()
                    .medicalRecord(existingRecord)
                    .name(medicationRequest.getName())
                    .startDate(medicationRequest.getStartDate())
                    .endDate(medicationRequest.getEndDate())
                    .position(medicationRequest.getPosition())
                    .frequency(medicationRequest.getFrequency())
                    .build();
            medicationRepository.save(medication);
        }

        for (DocumentRequest documentRequest : updateMedicalRecordRequest.getDocuments()) {
            Document document = Document.builder()
                    .medicalRecord(existingRecord)
                    .name(documentRequest.getName())
                    .position(documentRequest.getPosition())
                    .size(documentRequest.getSize())
                    .type(documentRequest.getType())
                    .path(documentRequest.getPath())
                    .build();
            documentRepository.save(document);
        }

        return existingRecord;
    }

    @Override
    public void deleteMedicalRecord(Integer medicalRecordID){
        MedicalRecord check = medicalRecordRepository.findById(medicalRecordID)
                .orElseThrow(() -> new IllegalArgumentException("Medical Record not found"));
        check.delete();
        medicalRecordRepository.save(check);
    }

    @Override
    public CustomPagination<MedicalRecordResponse> getAllMedicalRecords(int page, int size, String keyword, Integer userID, Long memberId){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<MedicalRecord> medicalRecords;
        if (memberId != null) {
            medicalRecords =  medicalRecordRepository.findByKeywordAndMemberId(keyword, userID, memberId, pageable);
        } else {
            medicalRecords =  medicalRecordRepository.findByKeyword(keyword, userID,pageable);
        }
        Page<MedicalRecordResponse> medicalRecordResponses = MedicalRecordMapper.INSTANCE.toMedicalRecordsResponse(medicalRecords);
        CustomPagination<MedicalRecordResponse> customPagination = new CustomPagination<>(medicalRecordResponses);
        return customPagination;
    }

    @Override
    public Optional<MedicalRecord> findMedicalRecordById(Integer medicalRecordID){
        return medicalRecordRepository.findById(medicalRecordID);
    }
}
