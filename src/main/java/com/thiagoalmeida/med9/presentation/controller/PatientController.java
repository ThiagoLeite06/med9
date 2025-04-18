package com.thiagoalmeida.med9.presentation.controller;

import com.thiagoalmeida.med9.application.dto.patient.PatientRequest;
import com.thiagoalmeida.med9.application.usecase.patient.CreatePatientUseCase;
import com.thiagoalmeida.med9.application.usecase.patient.GetPatientUseCase;
import com.thiagoalmeida.med9.domain.entity.Patient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final CreatePatientUseCase createPatientUseCase;
    private final GetPatientUseCase getPatientUseCase;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'NURSE')")
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid PatientRequest request) {
        return ResponseEntity.ok(createPatientUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
        return getPatientUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<Patient> getPatientByCpf(@PathVariable String cpf) {
        return getPatientUseCase.findByCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<Patient> getPatientByEmail(@PathVariable String email) {
        return getPatientUseCase.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
