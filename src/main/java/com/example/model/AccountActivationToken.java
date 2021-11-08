package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "acc_activation_token")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountActivationToken extends BaseEntity {
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "expires_at")
    private Instant expiresAt;
}
