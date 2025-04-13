package com.thiagoalmeida.med9.domain.entity;

import java.time.LocalDateTime;

import com.thiagoalmeida.med9.domain.enums.AppointmentStatus;

import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserJpaEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private UserJpaEntity patient;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
} 