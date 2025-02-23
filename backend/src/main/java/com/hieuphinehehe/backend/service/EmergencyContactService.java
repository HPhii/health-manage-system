package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.model.EmergencyContact;
import com.hieuphinehehe.backend.utils.CustomPagination;

public interface EmergencyContactService {
    EmergencyContact addEmergencyContact(EmergencyContact emergencyContact);
    EmergencyContact updateEmergencyContact(EmergencyContact emergencyContact);
    void deleteEmergencyContact(Integer contactID);
    EmergencyContact getEmergencyContactById(Integer contactID);
    CustomPagination<EmergencyContact> getAllEmergencyContacts(int page, int size, String keyword);

}
