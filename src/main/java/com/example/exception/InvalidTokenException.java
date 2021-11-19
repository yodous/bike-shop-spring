package com.example.exception;

public class InvalidTokenException extends RuntimeException {
    private static final String MESSAGE = "Invalid token";

    public InvalidTokenException() {
        super(MESSAGE);
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}
