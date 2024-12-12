package com.hieuphinehehe.backend.mapper;

import com.hieuphinehehe.backend.dto.request.healthStat.AddHealthStatRequest;
import com.hieuphinehehe.backend.dto.request.healthStat.UpdateHealthStatRequest;
import com.hieuphinehehe.backend.model.HealthStat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HealthStatMapper {
    HealthStatMapper INSTANCE = Mappers.getMapper(HealthStatMapper.class);

    @Mapping(source = "memberId", target = "member.id")
    HealthStat toHealthStat(AddHealthStatRequest request);

    @Mapping(target = "id", ignore = true) // id được set thủ công trong controller
    @Mapping(source = "statType", target = "statType")
    @Mapping(source = "statValue", target = "statValue")
    @Mapping(source = "date", target = "date")
    HealthStat toHealthStat(UpdateHealthStatRequest request);
}

