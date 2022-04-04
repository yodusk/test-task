package com.example.backend.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * User Role enum
 * */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }

}
