package com.thiagoalmeida.med9.application.dto.auth;

public record LoginResponse(
        String token,
        String username,
        String email,
        String name,
        String role)
{}
