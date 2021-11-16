package com.example.validation;

import com.example.exception.InvalidAddressEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest { //todo: this.pattern is null
    @Mock
    private Pattern pattern;
    @Mock
    private Matcher matcher;

    private final EmailValidator validator = new EmailValidator();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validAddressEmail_ThenOk() {
        List<String> validEmails = List.of(
                "username@domain.com",
                "user.name@domain.com",
                "user-name@domain.com",
                "username@domain.co.in",
                "user_name@domain.com");
        for (String email : validEmails)
            assertDoesNotThrow(() -> validator.isValid(email));
    }

    @Test
    void invalidAddressEmail_ThenThrowInvalidAddressEmailException() {
        List<String> invalidEmails = List.of(
                        "username.@domain.com" ,
                        ".user.name@domain.com",
                        "user-name@domain.com." ,
                        "username@.com");

        for (String email : invalidEmails)
            assertThrows(InvalidAddressEmailException.class, () -> validator.isValid(email));
    }
}