package com.example.exception;

public class InvalidPasswordException extends RuntimeException {
    private static final String MESSAGE = "Invalid password";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
