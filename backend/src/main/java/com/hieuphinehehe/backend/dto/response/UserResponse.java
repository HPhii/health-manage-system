package com.hieuphinehehe.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hieuphinehehe.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    int id;
    String firstname;
    String lastname;

    @JsonProperty("email")
    String email;

    private boolean is_verify;
    private boolean is_block;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    Role role;

    boolean vip;
}
