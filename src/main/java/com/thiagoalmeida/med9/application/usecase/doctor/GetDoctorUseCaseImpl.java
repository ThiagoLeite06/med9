package com.thiagoalmeida.med9.application.usecase.doctor;

import com.thiagoalmeida.med9.domain.entity.Doctor;
import com.thiagoalmeida.med9.domain.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetDoctorUseCaseImpl implements GetDoctorUseCase {
    private final DoctorRepository doctorRepository;

    @Override
    public Optional<Doctor> execute(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Optional<Doctor> findByCrm(String crm) {
        return doctorRepository.findByCrm(crm);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }
}
