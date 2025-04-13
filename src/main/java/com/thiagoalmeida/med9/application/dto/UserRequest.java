package com.thiagoalmeida.med9.application.dto;

import com.thiagoalmeida.med9.domain.enums.Role;

public record UserRequest(
        String username,
        String name,
        String email,
        String password,
        Role role,
        String address
) {}
