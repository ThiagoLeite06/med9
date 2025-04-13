package com.thiagoalmeida.med9.domain.entity;

import com.thiagoalmeida.med9.domain.enums.Role;

public record User(
        Long id,
        String username,
        String name,
        String email,
        String password,
        Role role,
        String address
) { }
