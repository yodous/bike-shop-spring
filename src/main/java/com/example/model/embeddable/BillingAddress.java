package com.example.model.embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BillingAddress {
    @Column(name = "full_name")
    @NotEmpty(message = "Name must not be empty")
    private String fullName;

    @NotEmpty(message = "Email must not be empty")
    private String email;

    @Embedded
    private Address address;
}
