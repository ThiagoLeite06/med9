package com.thiagoalmeida.med9.application.dto.user;

import com.thiagoalmeida.med9.domain.enums.Role;

public record UserResponse(
        Long id,
        String username,
        String name,
        String email,
        Role role,
        String address
) {}