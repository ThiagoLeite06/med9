package com.thiagoalmeida.med9.application.usecase;

import org.springframework.stereotype.Service;

import com.thiagoalmeida.med9.application.dto.CreateAppointmentRequest;
import com.thiagoalmeida.med9.domain.model.Appointment;
import com.thiagoalmeida.med9.domain.model.AppointmentStatus;
import com.thiagoalmeida.med9.domain.model.User;
import com.thiagoalmeida.med9.domain.repository.AppointmentRepository;
import com.thiagoalmeida.med9.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateAppointmentUseCase {
    
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    
    public Appointment execute(CreateAppointmentRequest request) {
        User doctor = userRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new RuntimeException("Doctor not found"));
            
        if (doctor.getRole() != com.thiagoalmeida.med9.domain.model.Role.DOCTOR) {
            throw new RuntimeException("User is not a doctor");
        }
        
        User patient = userRepository.findById(request.getPatientId())
            .orElseThrow(() -> new RuntimeException("Patient not found"));
            
        if (patient.getRole() != com.thiagoalmeida.med9.domain.model.Role.PATIENT) {
            throw new RuntimeException("User is not a patient");
        }
        
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setNotes(request.getNotes());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        
        return appointmentRepository.save(appointment);
    }
} 