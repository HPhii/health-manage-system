package com.hieuphinehehe.backend.dto.request.ai;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiRequest {
    @NotBlank(message = "Ask content is required")
    public String ask;
}
