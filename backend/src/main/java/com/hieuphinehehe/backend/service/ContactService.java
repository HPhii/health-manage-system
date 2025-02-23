package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.response.ContactResponse;
import com.hieuphinehehe.backend.model.Contact;
import org.springframework.data.domain.Page;

public interface ContactService {
    Page<ContactResponse> getAllContacts(int page, int size, String keyword);
    Contact updateSeenStateUser(Integer id);
    long countContactsReceivedToday();
}

