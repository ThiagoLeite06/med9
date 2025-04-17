package com.thiagoalmeida.med9.application.usecase.doctor;

import com.thiagoalmeida.med9.domain.entity.Doctor;
import java.util.Optional;

public interface GetDoctorUseCase {
    Optional<Doctor> execute(Long id);
    Optional<Doctor> findByCrm(String crm);
    Optional<Doctor> findByEmail(String email);
}
