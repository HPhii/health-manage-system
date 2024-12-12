package com.hieuphinehehe.backend.controller;

import com.hieuphinehehe.backend.dto.response.AllergyResponse;
import com.hieuphinehehe.backend.model.Allergy;
import com.hieuphinehehe.backend.model.User;
import com.hieuphinehehe.backend.model.Member;
import com.hieuphinehehe.backend.repository.MemberRepository;
import com.hieuphinehehe.backend.dto.request.allergy.AddAllergyRequest;
import com.hieuphinehehe.backend.dto.request.allergy.UpdateAllergyRequest;
import com.hieuphinehehe.backend.dto.response.ApiResponse;
import com.hieuphinehehe.backend.service.AuthenticationService;
import com.hieuphinehehe.backend.service.AllergyService;
import com.hieuphinehehe.backend.utils.CustomPagination;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/allergies")
@RequiredArgsConstructor
@Slf4j
public class AllergyController {
    private final AllergyService allergyService;
    private final AuthenticationService authenticationService;
    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> addAllergy(@Valid @RequestBody AddAllergyRequest addAllergyRequest) {
        Member member = memberRepository.findById(addAllergyRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        addAllergyRequest.setMember(member);
        Allergy createdAllergy = allergyService.addAllergy(addAllergyRequest);
        ApiResponse<Allergy> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Adding new allergy successfully",
                createdAllergy
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateAllergy(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UpdateAllergyRequest updateAllergyRequest) {
        Member member = memberRepository.findById(updateAllergyRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Allergy allergy = Allergy.builder()
                .id(id)
                .member(member)
                .allergyType(updateAllergyRequest.getAllergyType())
                .severity(updateAllergyRequest.getSeverity())
                .symptoms(updateAllergyRequest.getSymptoms())
                .build();
        Allergy updatedAllergy = allergyService.updateAllergy(allergy);
        ApiResponse<Allergy> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Update allergy successfully",
                updatedAllergy
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllergy(@PathVariable("id") Integer id) {
        allergyService. deleteAllergy(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getAllergyById(@PathVariable("id") Integer id) {
        Optional<Allergy> allergy = allergyService.findAllergyById(id);
        ApiResponse<Optional<Allergy>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get allergy successfully",
                allergy
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<CustomPagination<AllergyResponse>>> getAllAllergies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Long memberId) {
        User user = authenticationService.getCurrentUser();
        CustomPagination<AllergyResponse> alleriesPage = allergyService.getAllAllergies(page,size,keyword, user.getId(), memberId);
        ApiResponse<CustomPagination<AllergyResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Get list of allergy successfully",
                alleriesPage
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
