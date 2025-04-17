package com.thiagoalmeida.med9.domain.repository;

import com.thiagoalmeida.med9.domain.entity.Patient;
import java.util.Optional;
import java.util.List;

public interface PatientRepository {
    Patient save(Patient patient);
    Optional<Patient> findById(Long id);
    Optional<Patient> findByCpf(String cpf);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByUserId(Long userId);
    List<Patient> findAll();
    void deleteById(Long id);
}
