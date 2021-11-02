package com.example.validation;

import com.example.exception.InvalidPasswordException;

public class PasswordValidator {

    public static void isValid(String password) {
        //at least 6 characters
        if (password.length() < 6)
            throw new InvalidPasswordException(
                    "Password must be at least 6 characters long");

        //at least one upper case letter
        if (!password.contains("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)"))
            throw new InvalidPasswordException(
                    "Password must consist of at least one upper case letter");

        //at least one digit
        if (!password.contains("\\d"))
            throw new InvalidPasswordException(
                    "Password must consist of at least one digit");

        //must not contain spaces
        if (password.contains(" "))
            throw new InvalidPasswordException(
                    "Password must not contain any spaces");

        //at least one special character
        if (!password.contains("(?=.*[!@#$%^&*])"))
            throw new InvalidPasswordException(
                    "Password must contain at least one special character");
    }

    public static void arePasswordsTheSame(String password0, String password1) {
        if (!password0.equals(password1))
            throw new InvalidPasswordException("Passwords are not the same");
    }

}

