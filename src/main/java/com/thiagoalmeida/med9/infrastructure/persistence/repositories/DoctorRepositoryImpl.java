package com.thiagoalmeida.med9.infrastructure.persistence.repositories;

import com.thiagoalmeida.med9.domain.entity.Doctor;
import com.thiagoalmeida.med9.domain.repository.DoctorRepository;
import com.thiagoalmeida.med9.infrastructure.persistence.entities.DoctorJpaEntity;
import com.thiagoalmeida.med9.infrastructure.mapper.DoctorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DoctorRepositoryImpl implements DoctorRepository {
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Doctor save(Doctor doctor) {
        DoctorJpaEntity entity = doctorMapper.toEntity(doctor);
        return doctorMapper.toDomain(doctorJpaRepository.save(entity));
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorJpaRepository.findById(id)
                .map(doctorMapper::toDomain);
    }

    @Override
    public Optional<Doctor> findByCrm(String crm) {
        return doctorJpaRepository.findByCrm(crm)
                .map(doctorMapper::toDomain);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorJpaRepository.findByEmail(email)
                .map(doctorMapper::toDomain);
    }

    @Override
    public Optional<Doctor> findByUserId(Long userId) {
        return doctorJpaRepository.findByUserId(userId)
                .map(doctorMapper::toDomain);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        doctorJpaRepository.deleteById(id);
    }
}
