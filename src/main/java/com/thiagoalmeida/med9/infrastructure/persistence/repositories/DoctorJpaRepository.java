package com.thiagoalmeida.med9.infrastructure.persistence.repositories;

import com.thiagoalmeida.med9.infrastructure.persistence.entities.DoctorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorJpaEntity, Long> {
    Optional<DoctorJpaEntity> findByCrm(String crm);
    Optional<DoctorJpaEntity> findByEmail(String email);
    Optional<DoctorJpaEntity> findByUserId(Long userId);
}
