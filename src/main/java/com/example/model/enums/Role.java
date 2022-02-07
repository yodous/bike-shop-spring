package com.example.model.enums;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements GrantedAuthority {
    USER("READ_PRIVILEGE"),
    ADMIN("WRITE_PRIVILEGE");

	// TODO ML: final? Otherwise this value could be changed in runtime but I believe You do not want this; final Role admin = Role.ADMIN;assertThat(admin.getAuthority()).isEqualTo("WRITE_PRIVILEGE");admin.setAuthority("bam");assertThat(admin.getAuthority()).isEqualTo("bam");
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

}


