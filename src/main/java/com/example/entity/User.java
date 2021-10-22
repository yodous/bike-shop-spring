package com.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Username must not be empty")
    @Max(value = 20, message = "Username must be not longer than 20 characters")
    @Column(name = "username", unique = true)
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "First name must not be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(name = "email_address", unique = true)
    private String email;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @Embedded
    private Address address;

}
