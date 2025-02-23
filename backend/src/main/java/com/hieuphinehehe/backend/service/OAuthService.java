package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.response.AuthenticationResponse;

public interface OAuthService {
    AuthenticationResponse authenticate(String credential);
}
