package com.hieuphinehehe.backend.service.impl;

import com.hieuphinehehe.backend.model.Appointment;
import com.hieuphinehehe.backend.repository.AppointmentRepository;
import com.hieuphinehehe.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment addAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment){
        Appointment check = appointmentRepository.findById(appointment.getId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Integer appointmentID){
        Appointment check = appointmentRepository.findById(appointmentID)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
        appointmentRepository.deleteById(appointmentID);
    }

    @Override
    public Appointment getAppointmentById(Integer appointmentID){
        return appointmentRepository.findById(appointmentID)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
    }

    @Override
    public Page<Appointment> getAllAppointments(int page, int size, String keyword){
        Pageable pageable = PageRequest.of(page - 1, size);
        return appointmentRepository.findByKeyword(keyword, pageable);
    }

}
