package com.thiagoalmeida.med9.application.dto;

import java.time.LocalDateTime;

import com.thiagoalmeida.med9.domain.model.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
    private LocalDateTime appointmentDateTime;
    private String notes;
    private AppointmentStatus status;
} 