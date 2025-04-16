package com.thiagoalmeida.med9.domain.entity;

import com.thiagoalmeida.med9.domain.enums.Role;

import java.util.Set;

public record User(
        Long id,
        String username,
        String name,
        String email,
        String password,
        Set<String> roles,
        String address
) { }
