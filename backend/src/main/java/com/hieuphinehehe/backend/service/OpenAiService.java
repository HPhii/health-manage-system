package com.hieuphinehehe.backend.service;

public interface OpenAiService {
    String askQuestion(String question);

    String extractTextFromResponse(String responseBody);
}