package com.example.validation;

import com.example.exception.InvalidAddressEmailException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {
    private static final String REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private EmailValidator() {
    }

    public void isValid(String email) {
        Pattern emailPattern = Pattern.compile(REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches())
            throw new InvalidAddressEmailException();
    }
}
