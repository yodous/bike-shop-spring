package com.example.model.embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Embeddable
@Getter
@Setter
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
    @Pattern(regexp = "d{2}-d{3}",
            message = "Postal code must be in format xx-xxx")
    @Column(name = "postal_code")
    private String postalCode;
}
