package com.example.exception;

public class ProductNotFoundException extends RuntimeException{
    public static final String MESSAGE = "Could not find product";

    public ProductNotFoundException() {
        super(MESSAGE);
    }

}
