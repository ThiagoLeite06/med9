package com.thiagoalmeida.med9.domain.entity;

import com.thiagoalmeida.med9.domain.enums.Role;

public record User(
        Long id,
        String username,
        String password,
        String name,
        String email,
        Role role,
        String address
) { }
