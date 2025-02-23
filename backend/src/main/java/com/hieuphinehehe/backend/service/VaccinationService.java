package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.model.Vaccication;
import org.springframework.data.domain.Page;


public interface VaccinationService {
    Vaccication addVaccication(Vaccication member);
    Vaccication updateVaccication(Vaccication member);
    void deleteVaccication(Integer memberID);
    Vaccication getVaccicationById(Integer memberID);
    Page<Vaccication> getAllVaccications(int page, int size, int userId, String keyword, Long memberId);
}
