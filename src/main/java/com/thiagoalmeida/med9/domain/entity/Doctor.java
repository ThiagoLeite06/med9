package com.thiagoalmeida.med9.domain.entity;

public record Doctor(
    Long id,
    String name,
    String crm,
    String specialty,
    String phone,
    String email,
    Long userId
) {}