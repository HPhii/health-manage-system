package com.hieuphinehehe.backend.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hieuphinehehe.backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Firstname is mandatory")
  @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
  private String firstname;

  @NotBlank(message = "Lastname is mandatory")
  @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
  private String lastname;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
  private String password;

  private Role role;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime date;
}
