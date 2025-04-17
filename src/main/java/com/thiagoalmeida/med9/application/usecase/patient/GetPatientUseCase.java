package com.thiagoalmeida.med9.application.usecase.patient;

import com.thiagoalmeida.med9.domain.entity.Patient;
import java.util.Optional;

public interface GetPatientUseCase {
    Optional<Patient> execute(Long id);
    Optional<Patient> findByCpf(String cpf);
    Optional<Patient> findByEmail(String email);
}
