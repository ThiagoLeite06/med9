package com.thiagoalmeida.med9.infrastructure.persistence.repositories;

import com.thiagoalmeida.med9.domain.entity.Patient;
import com.thiagoalmeida.med9.domain.repository.PatientRepository;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.PatientJpaEntity;
import com.thiagoalmeida.med9.infrastructure.mapper.PatientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {
    private final PatientJpaRepository patientJpaRepository;
    private final PatientMapper patientMapper;

    @Override
    public Patient save(Patient patient) {
        PatientJpaEntity entity = patientMapper.toEntity(patient);
        return patientMapper.toDomain(patientJpaRepository.save(entity));
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientJpaRepository.findById(id)
                .map(patientMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByCpf(String cpf) {
        return patientJpaRepository.findByCpf(cpf)
                .map(patientMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email)
                .map(patientMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByUserId(Long userId) {
        return patientJpaRepository.findByUserId(userId)
                .map(patientMapper::toDomain);
    }

    @Override
    public List<Patient> findAll() {
        return patientJpaRepository.findAll().stream()
                .map(patientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        patientJpaRepository.deleteById(id);
    }
}
