package com.example.validation;

import com.example.exception.InvalidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String UPPER_CASE_REGEX = ".*[A-Z].*";
    private static final String AT_LEAST_ONE_DIGIT = ".*\\d.*";
    private static final String AT_LEAST_ONE_SPECIAL_CHARACTER = ".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*";

    public static void isValid(String password, String confirmPassword) {
        containsDigit(password);
        containsUpperCaseLetter(password);
        containsSpecialChar(password);
        longEnough(password);
        noSpaces(password);
        passwordsTheSame(password, confirmPassword);
    }

    private static void longEnough(String password) {
        if (password.length() < 6)
            throw new InvalidPasswordException(
                    "Password must be at least 6 characters long");
    }

    private static void noSpaces(String password) {
        if (password.contains(" "))
            throw new InvalidPasswordException(
                    "Password must not contain any spaces");
    }

    private static void containsDigit(String password) {
        Pattern digitPattern = Pattern.compile(AT_LEAST_ONE_DIGIT);
        Matcher digitMatcher = digitPattern.matcher(password);

        if (!digitMatcher.matches())
            throw new InvalidPasswordException(
                    "Password must consist of at least one digit");
    }

    private static void containsUpperCaseLetter(String password) {
        Pattern upperPattern = Pattern.compile(UPPER_CASE_REGEX);
        Matcher upperCaseMatcher = upperPattern.matcher(password);

        if (!upperCaseMatcher.matches())
            throw new InvalidPasswordException(
                    "Password must consist of at least one upper case letter");
    }

    private static void containsSpecialChar(String password) {
        Pattern specialCharPattern = Pattern.compile(AT_LEAST_ONE_SPECIAL_CHARACTER);
        Matcher specialCharMatcher = specialCharPattern.matcher(password);

        if (!specialCharMatcher.matches())
            throw new InvalidPasswordException(
                    "Password must contain at least one special character");
    }

    public static void passwordsTheSame(String password0, String password1) {
        if (!password0.equals(password1))
            throw new InvalidPasswordException("Passwords are not the same");
    }

}

