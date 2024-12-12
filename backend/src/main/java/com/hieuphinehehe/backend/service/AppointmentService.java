package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.model.Appointment;
import org.springframework.data.domain.Page;


public interface AppointmentService {
    Appointment addAppointment(Appointment appointment);
    Appointment updateAppointment(Appointment appointment);
    void deleteAppointment(Integer appointmentID);
    Appointment getAppointmentById(Integer appointmentID);
    Page<Appointment> getAllAppointments(int page, int size, String keyword);
}
