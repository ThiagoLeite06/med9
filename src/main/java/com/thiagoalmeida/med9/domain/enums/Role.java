package com.thiagoalmeida.med9.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    DOCTOR,
    NURSE,
    PATIENT;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}