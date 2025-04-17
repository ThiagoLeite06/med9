package com.thiagoalmeida.med9.application.usecase.doctor;

import com.thiagoalmeida.med9.domain.entity.Doctor;
import com.thiagoalmeida.med9.application.dto.doctor.DoctorRequest;

public interface CreateDoctorUseCase {
    Doctor execute(DoctorRequest request);
}
