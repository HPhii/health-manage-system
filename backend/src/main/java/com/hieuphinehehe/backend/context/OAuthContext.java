package com.hieuphinehehe.backend.context;

import com.hieuphinehehe.backend.dto.response.OAuthUser;
import com.hieuphinehehe.backend.strategies.OAuthStrategy;

public class OAuthContext {
    private final OAuthStrategy oAuthStrategy;

    public OAuthContext(OAuthStrategy oAuthStrategy) {
        this.oAuthStrategy = oAuthStrategy;
    }

    public OAuthUser authenticate(String credential) {
        return oAuthStrategy.authenticate(credential);
    }
}
