package com.hieuphinehehe.backend.controller;

import com.hieuphinehehe.backend.model.EmergencyContact;
import com.hieuphinehehe.backend.model.User;
import com.hieuphinehehe.backend.dto.request.emergencyContact.AddEmergencyContactRequest;
import com.hieuphinehehe.backend.dto.request.emergencyContact.UpdateEmergencyContactRequest;
import com.hieuphinehehe.backend.dto.response.ApiResponse;
import com.hieuphinehehe.backend.service.AuthenticationService;
import com.hieuphinehehe.backend.service.EmergencyContactService;
import com.hieuphinehehe.backend.utils.CustomPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emergencyContacts")
@RequiredArgsConstructor
@Slf4j

public class EmergencyContactController {
    private final EmergencyContactService emergencyContactService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addEmergencyContact(@Valid @RequestBody AddEmergencyContactRequest addEmergencyContactRequest){
        User user = authenticationService.getCurrentUser();
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "User not found", null), HttpStatus.UNAUTHORIZED);
        }
        EmergencyContact emergencyContact = EmergencyContact.builder()
                .user(user)
                .name(addEmergencyContactRequest.getName())
                .relationship(addEmergencyContactRequest.getRelationship())
                .phoneNumber(addEmergencyContactRequest.getPhoneNumber())
                .build();
        log.info(emergencyContact.toString());
        EmergencyContact createdEmergencyContact =emergencyContactService.addEmergencyContact(emergencyContact);
        ApiResponse<EmergencyContact> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get list emergencyContact successfully",
                createdEmergencyContact
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse <?> > updateEmergencyContact(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UpdateEmergencyContactRequest updateEmergencyContactRequest){

        EmergencyContact emergencyContact = EmergencyContact.builder()
                .id(id)
                .name(updateEmergencyContactRequest.getName())
                .relationship(updateEmergencyContactRequest.getRelationship())
                .phoneNumber(updateEmergencyContactRequest.getPhoneNumber())
                .build();
        EmergencyContact updateEmergencyContact = emergencyContactService.updateEmergencyContact(emergencyContact);
        ApiResponse<EmergencyContact> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Update emergency contact successfully",
                updateEmergencyContact
        );
        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmergencyContact(@PathVariable("id") Integer id) {
        try {
            EmergencyContact contact = emergencyContactService.getEmergencyContactById(id);
            if (contact == null) {
                return new ResponseEntity<>(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Emergency contact not found", null), HttpStatus.NOT_FOUND);
            }

            emergencyContactService.deleteEmergencyContact(id);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Emergency contact deleted successfully", null), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete emergency contact: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public  ResponseEntity<ApiResponse<CustomPagination<EmergencyContact>>> getAllEmergencyContacts(
            @RequestParam(defaultValue =  "1") int page,
            @RequestParam(defaultValue =  "8") int size,
            @RequestParam(defaultValue =  "")  String keyword){

        CustomPagination<EmergencyContact> emergencyContactsPage = emergencyContactService.getAllEmergencyContacts(page, size, keyword);
        ApiResponse<CustomPagination<EmergencyContact>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get list emergency contact successfully",
                emergencyContactsPage
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
