package com.thiagoalmeida.med9.domain.entity;

public record Patient(
    Long id,
    String name,
    String cpf,
    String phone,
    String email
) {}