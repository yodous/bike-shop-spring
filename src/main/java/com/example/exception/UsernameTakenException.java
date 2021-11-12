package com.example.exception;

public class UsernameTakenException extends RuntimeException {
    private static final String MESSAGE = "Username or password is not valid";

    public UsernameTakenException() {
        super(MESSAGE);
    }

}
