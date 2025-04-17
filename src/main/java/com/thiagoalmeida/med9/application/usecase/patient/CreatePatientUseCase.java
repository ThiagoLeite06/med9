package com.thiagoalmeida.med9.application.usecase.patient;

import com.thiagoalmeida.med9.domain.entity.Patient;
import com.thiagoalmeida.med9.application.dto.patient.PatientRequest;

public interface CreatePatientUseCase {
    Patient execute(PatientRequest request);
}
