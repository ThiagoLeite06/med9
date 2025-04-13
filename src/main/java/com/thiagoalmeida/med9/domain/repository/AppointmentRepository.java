package com.thiagoalmeida.med9.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagoalmeida.med9.domain.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(UserJpaEntity patient);
    List<Appointment> findByDoctor(UserJpaEntity doctor);
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Appointment> findByDoctorAndAppointmentDateTimeBetween(UserJpaEntity doctor, LocalDateTime startDate, LocalDateTime endDate);
    List<Appointment> findByPatientAndAppointmentDateTimeBetween(UserJpaEntity patient, LocalDateTime startDate, LocalDateTime endDate);
} 