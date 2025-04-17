package com.thiagoalmeida.med9.application.usecase.doctor;

import com.thiagoalmeida.med9.application.dto.doctor.DoctorRequest;
import com.thiagoalmeida.med9.domain.entity.Doctor;
import com.thiagoalmeida.med9.domain.entity.User;
import com.thiagoalmeida.med9.domain.enums.Role;
import com.thiagoalmeida.med9.domain.repository.DoctorRepository;
import com.thiagoalmeida.med9.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateDoctorUseCaseImpl implements CreateDoctorUseCase {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Doctor execute(DoctorRequest request) {
        doctorRepository.findByCrm(request.crm())
            .ifPresent(d -> {
                throw new IllegalArgumentException("CRM já cadastrado");
            });

        doctorRepository.findByEmail(request.email())
            .ifPresent(d -> {
                throw new IllegalArgumentException("Email já cadastrado");
            });

        User user = new User(
            null,
            request.username(),
            passwordEncoder.encode(request.password()),
            request.name(),
            request.email(),
            Role.DOCTOR,
            request.address()
        );
        user = userRepository.save(user);

        Doctor doctor = new Doctor(
            null,
            request.name(),
            request.crm(),
            request.specialty(),
            request.phone(),
            request.email()
        );
        
        return doctorRepository.save(doctor);
    }
}
