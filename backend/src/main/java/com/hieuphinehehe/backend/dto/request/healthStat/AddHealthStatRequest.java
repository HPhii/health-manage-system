package com.hieuphinehehe.backend.dto.request.healthStat;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddHealthStatRequest {

    private Integer id;

    private Integer memberId;

    private String statType;

    @NotNull(message = "Status value is required")
    private float statValue;

    @NotNull(message = "Date and time is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
}
