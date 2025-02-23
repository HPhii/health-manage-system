package com.hieuphinehehe.backend.service;

public interface ForgotPasswordService {
    String sendOTP(String email);
    String sendNewPassword(String email, String otp);
}
