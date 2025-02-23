package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.response.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailService {
    void sendHTMLMail(DataMailDTO dataMail, String templateName) throws MessagingException;
}
