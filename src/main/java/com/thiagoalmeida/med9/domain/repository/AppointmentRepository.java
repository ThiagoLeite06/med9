package com.thiagoalmeida.med9.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagoalmeida.med9.domain.model.Appointment;
import com.thiagoalmeida.med9.domain.model.User;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(User doctor);
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
} 