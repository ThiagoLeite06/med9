package com.thiagoalmeida.med9.application.usecase.patient;

import com.thiagoalmeida.med9.application.dto.patient.PatientRequest;
import com.thiagoalmeida.med9.domain.entity.Patient;
import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.enums.Role;
import com.thiagoalmeida.med9.domain.repository.PatientRepository;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePatientUseCaseImpl implements CreatePatientUseCase {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Patient execute(PatientRequest request) {
        patientRepository.findByCpf(request.cpf())
            .ifPresent(p -> {
                throw new IllegalArgumentException("CPF já cadastrado");
            });

        patientRepository.findByEmail(request.email())
            .ifPresent(p -> {
                throw new IllegalArgumentException("Email já cadastrado");
            });

        User user = new User(
            null,
            request.username(),
            passwordEncoder.encode(request.password()),
            request.name(),
            request.email(),
            Role.PATIENT, request.address()
        );
        user = userRepository.save(user);

        Patient patient = new Patient(
            null,
            request.name(),
            request.cpf(),
            request.phone(),
            request.email()
        );
        
        return patientRepository.save(patient);
    }
}
