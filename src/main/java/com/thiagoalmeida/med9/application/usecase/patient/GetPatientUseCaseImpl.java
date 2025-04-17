package com.thiagoalmeida.med9.application.usecase.patient;

import com.thiagoalmeida.med9.domain.entity.Patient;
import com.thiagoalmeida.med9.domain.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetPatientUseCaseImpl implements GetPatientUseCase {
    private final PatientRepository patientRepository;

    @Override
    public Optional<Patient> execute(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        return patientRepository.findByCpf(cpf);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
}
