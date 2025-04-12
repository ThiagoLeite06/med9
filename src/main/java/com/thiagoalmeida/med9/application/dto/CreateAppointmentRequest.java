package com.thiagoalmeida.med9.application.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAppointmentRequest {
    
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotNull(message = "Appointment date and time is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDateTime;
    
    private String notes;
} 