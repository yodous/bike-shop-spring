package com.example.exception;

public class InvalidAddressEmailException extends RuntimeException {
    private static final String MESSAGE = "Address email is not valid";

    public InvalidAddressEmailException() {
        super(MESSAGE);
    }

}
