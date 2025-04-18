package com.thiagoalmeida.med9.presentation.controller;

import com.thiagoalmeida.med9.application.dto.doctor.DoctorRequest;
import com.thiagoalmeida.med9.application.usecase.doctor.CreateDoctorUseCase;
import com.thiagoalmeida.med9.application.usecase.doctor.GetDoctorUseCase;
import com.thiagoalmeida.med9.domain.entity.Doctor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final CreateDoctorUseCase createDoctorUseCase;
    private final GetDoctorUseCase getDoctorUseCase;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Doctor> createDoctor(@RequestBody @Valid DoctorRequest request) {
        return ResponseEntity.ok(createDoctorUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<Doctor> getDoctor(@PathVariable Long id) {
        return getDoctorUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/crm/{crm}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<Doctor> getDoctorByCrm(@PathVariable String crm) {
        return getDoctorUseCase.findByCrm(crm)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'NURSE')")
    public ResponseEntity<Doctor> getDoctorByEmail(@PathVariable String email) {
        return getDoctorUseCase.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
