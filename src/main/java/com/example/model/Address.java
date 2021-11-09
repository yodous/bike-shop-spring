package com.example.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @NotEmpty(message = "City must not be empty")
    @Column(name = "city")
    private String city;

    @NotEmpty(message = "Street must not be empty")
    @Column(name = "street")
    private String street;

    @NotEmpty(message = "Zip code must not be empty")
    @Pattern(regexp = "d{2}-d{3}", message = "Postal code must be in format xx-xxx where x is any digit")
    @Column(name = "postal_code")
    private String postalCode;
}
