package com.example.exception;

public class TokenNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Could not find token";

    public TokenNotFoundException() {
        super(MESSAGE);
    }

    public TokenNotFoundException(String message) {
        super(message);
    }

}
