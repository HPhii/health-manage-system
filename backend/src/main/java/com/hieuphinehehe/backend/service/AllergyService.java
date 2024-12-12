package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.request.allergy.AddAllergyRequest;
import com.hieuphinehehe.backend.dto.response.AllergyResponse;
import com.hieuphinehehe.backend.model.Allergy;
import com.hieuphinehehe.backend.utils.CustomPagination;

import java.util.Optional;

public interface AllergyService {
    Allergy addAllergy(AddAllergyRequest allergy);
    Allergy updateAllergy(Allergy allergy);
    void deleteAllergy(Integer allergyID);
    CustomPagination<AllergyResponse> getAllAllergies(int page, int size, String keyword, Integer userID, Long memberId);
    Optional <Allergy> findAllergyById(Integer allergyID);
}
