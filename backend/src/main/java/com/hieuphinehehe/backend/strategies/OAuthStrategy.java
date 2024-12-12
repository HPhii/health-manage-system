package com.hieuphinehehe.backend.strategies;

import com.hieuphinehehe.backend.dto.response.OAuthUser;

public interface OAuthStrategy {
    OAuthUser authenticate(String credential);
}
