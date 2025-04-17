package com.thiagoalmeida.med9.domain.repository;

import com.thiagoalmeida.med9.domain.entity.Doctor;
import java.util.Optional;
import java.util.List;

public interface DoctorRepository {
    Doctor save(Doctor doctor);
    Optional<Doctor> findById(Long id);
    Optional<Doctor> findByCrm(String crm);
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByUserId(Long userId);
    List<Doctor> findAll();
    void deleteById(Long id);
}
