package com.thiagoalmeida.med9.application.usecase;

import com.thiagoalmeida.med9.domain.enums.Role;
import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.AppointmentNotification;
import com.thiagoalmeida.med9.application.dto.CreateAppointmentRequest;
import com.thiagoalmeida.med9.domain.entity.Appointment;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import com.thiagoalmeida.med9.domain.repository.AppointmentRepository;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import com.thiagoalmeida.med9.infrastructure.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCase {
    
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    
    public Appointment execute(CreateAppointmentRequest request) {
        UserJpaEntity doctor = userRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
        if (doctor.getRole() != Role.DOCTOR) {
            throw new RuntimeException("User is not a doctor");
        }
        
        UserJpaEntity patient = userRepository.findById(request.getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient not found"));
            
        if (patient.getRole() != Role.PATIENT) {
            throw new RuntimeException("User is not a patient");
        }
        
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setNotes(request.getNotes());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        
        Appointment savedAppointment = appointmentRepository.save(appointment);
        
        // Enviar notificação
        AppointmentNotification notification = new AppointmentNotification(
            savedAppointment.getId(),
            patient.getId(),
            patient.getName(),
            patient.getEmail(),
            doctor.getId(),
            doctor.getName(),
            savedAppointment.getAppointmentDateTime(),
            savedAppointment.getNotes()
        );
        
        notificationService.sendAppointmentNotification(notification);
        
        return savedAppointment;
    }
} 