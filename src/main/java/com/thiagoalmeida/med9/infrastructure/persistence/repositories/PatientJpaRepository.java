package com.thiagoalmeida.med9.infrastructure.persistence.repositories;

import com.thiagoalmeida.med9.infrastructure.persistence.entities.PatientJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientJpaEntity, Long> {
    Optional<PatientJpaEntity> findByCpf(String cpf);
    Optional<PatientJpaEntity> findByEmail(String email);
    Optional<PatientJpaEntity> findByUserId(Long userId);
}
