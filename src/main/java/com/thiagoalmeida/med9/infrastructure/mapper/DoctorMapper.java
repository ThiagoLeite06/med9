package com.thiagoalmeida.med9.infrastructure.mapper;

import com.thiagoalmeida.med9.domain.entity.Doctor;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.DoctorJpaEntity;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    
    public Doctor toDomain(DoctorJpaEntity entity) {
        if (entity == null) return null;
        return new Doctor(
            entity.getId(),
            entity.getName(),
            entity.getCrm(),
            entity.getSpecialty(),
            entity.getPhone(),
            entity.getEmail(),
            entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

    public DoctorJpaEntity toEntity(Doctor doctor) {
        if (doctor == null) return null;
        DoctorJpaEntity entity = DoctorJpaEntity.builder()
            .id(doctor.id())
            .name(doctor.name())
            .crm(doctor.crm())
            .specialty(doctor.specialty())
            .phone(doctor.phone())
            .email(doctor.email())
            .build();
        
        if (doctor.userId() != null) {
            UserJpaEntity userEntity = new UserJpaEntity();
            userEntity.setId(doctor.userId());
            entity.setUser(userEntity);
        }
        
        return entity;
    }
}
