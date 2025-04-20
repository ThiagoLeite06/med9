package com.thiagoalmeida.med9.infrastructure.mapper;

import com.thiagoalmeida.med9.domain.entity.Patient;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.PatientJpaEntity;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    
    public Patient toDomain(PatientJpaEntity entity) {
        if (entity == null) return null;
        return new Patient(
            entity.getId(),
            entity.getName(),
            entity.getCpf(),
            entity.getPhone(),
            entity.getEmail(),
            entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    public PatientJpaEntity toEntity(Patient patient) {
        if (patient == null) return null;
        PatientJpaEntity entity = PatientJpaEntity.builder()
            .id(patient.id())
            .name(patient.name())
            .cpf(patient.cpf())
            .phone(patient.phone())
            .email(patient.email())
            .build();
        
        if (patient.userId() != null) {
            UserJpaEntity userEntity = new UserJpaEntity();
            userEntity.setId(patient.userId());
            entity.setUser(userEntity);
        }
        
        return entity;
    }
}
