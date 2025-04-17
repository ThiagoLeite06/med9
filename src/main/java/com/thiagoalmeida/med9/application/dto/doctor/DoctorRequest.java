package com.thiagoalmeida.med9.application.dto.doctor;

public record DoctorRequest(
    String name,
    String crm,
    String specialty,
    String phone,
    String email,
    String username,
    String password,
    String address
) {}
