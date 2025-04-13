package com.thiagoalmeida.med9.presentation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thiagoalmeida.med9.application.dto.AppointmentResponse;
import com.thiagoalmeida.med9.application.dto.CreateAppointmentRequest;
import com.thiagoalmeida.med9.application.dto.UpdateAppointmentRequest;
import com.thiagoalmeida.med9.application.usecase.CreateAppointmentUseCase;
import com.thiagoalmeida.med9.application.usecase.ListAppointmentsUseCase;
import com.thiagoalmeida.med9.application.usecase.UpdateAppointmentUseCase;
import com.thiagoalmeida.med9.domain.entity.Appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    
    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final ListAppointmentsUseCase listAppointmentsUseCase;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;
    
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
    
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> listAppointments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<AppointmentResponse> appointments = listAppointmentsUseCase.execute(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAppointmentRequest request) {
        AppointmentResponse response = updateAppointmentUseCase.execute(id, request);
        return ResponseEntity.ok(response);
    }
} 