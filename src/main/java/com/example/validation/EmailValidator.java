package com.example.validation;

import com.example.exception.InvalidAddressEmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {
    @Value("${email.regex}")
    private String regex;

    public void isValid(String email) {
        Pattern emailPattern = Pattern.compile(regex);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches())
            throw new InvalidAddressEmailException();
    }
}
