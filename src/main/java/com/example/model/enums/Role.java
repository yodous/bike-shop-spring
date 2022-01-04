package com.example.model.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    USER("READ_PRIVILEGE"),
    ADMIN("WRITE_PRIVILEGE");

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}


