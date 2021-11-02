package com.example.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

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

    @Embedded
    private Address address;

    public User(String username, String password, String firstName, String lastName, String email, Address address) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }
}
