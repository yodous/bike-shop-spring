package com.example.validation;

import com.example.exception.InvalidAddressEmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static void isValid(String email) {
        Pattern emailPattern = Pattern.compile(REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);

        if (!emailMatcher.matches())
            throw new InvalidAddressEmailException();
    }
}
