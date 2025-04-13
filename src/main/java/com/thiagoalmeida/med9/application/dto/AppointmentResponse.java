package com.thiagoalmeida.med9.application.dto;

import java.time.LocalDateTime;

import com.thiagoalmeida.med9.domain.enums.AppointmentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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