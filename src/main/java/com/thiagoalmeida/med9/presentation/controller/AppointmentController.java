package com.thiagoalmeida.med9.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagoalmeida.med9.application.dto.AppointmentResponse;
import com.thiagoalmeida.med9.application.dto.CreateAppointmentRequest;
import com.thiagoalmeida.med9.application.usecase.CreateAppointmentUseCase;
import com.thiagoalmeida.med9.domain.model.Appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    
    private final CreateAppointmentUseCase createAppointmentUseCase;
    
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = createAppointmentUseCase.execute(request);
        
        AppointmentResponse response = new AppointmentResponse(
            appointment.getId(),
            appointment.getDoctor().getId(),
            appointment.getDoctor().getName(),
            appointment.getPatient().getId(),
            appointment.getPatient().getName(),
            appointment.getAppointmentDateTime(),
            appointment.getNotes(),
            appointment.getStatus()
        );
        
        return ResponseEntity.ok(response);
    }
} 