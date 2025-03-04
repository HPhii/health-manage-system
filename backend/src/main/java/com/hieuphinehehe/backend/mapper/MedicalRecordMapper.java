package com.hieuphinehehe.backend.mapper;

import com.hieuphinehehe.backend.dto.response.MedicalRecordResponse;
import com.hieuphinehehe.backend.model.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    MedicalRecordMapper INSTANCE = Mappers.getMapper( MedicalRecordMapper.class );

    MedicalRecordResponse toMedicalRecordResponse(MedicalRecord medicalRecord);

    default Page<MedicalRecordResponse> toMedicalRecordsResponse(Page<MedicalRecord> medicalRecords){
        return medicalRecords.map(this::toMedicalRecordResponse);
    };
}
