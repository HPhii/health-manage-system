package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.request.auth.RegisterRequest;
import com.hieuphinehehe.backend.dto.request.auth.LoginRequest;
import com.hieuphinehehe.backend.model.User;
import com.hieuphinehehe.backend.dto.response.ApiResponse;
import com.hieuphinehehe.backend.dto.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthenticationService {

  AuthenticationResponse register(RegisterRequest request);

  AuthenticationResponse authenticate(LoginRequest request);

  void verifyEmail(String token);

  void saveUserToken(User user, String jwtToken);

  void revokeAllUserTokens(User user);

  ResponseEntity<ApiResponse<?>> refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException;

  ResponseEntity<ApiResponse<?>> getMe(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException;

  public User getCurrentUser();
}
