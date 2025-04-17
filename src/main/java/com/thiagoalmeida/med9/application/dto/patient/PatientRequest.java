package com.thiagoalmeida.med9.application.dto.patient;

public record PatientRequest(
    String name,
    String cpf,
    String phone,
    String email,
    String username,
    String password,
    String address
) {}
