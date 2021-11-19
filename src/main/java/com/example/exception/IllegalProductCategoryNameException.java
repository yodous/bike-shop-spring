package com.example.exception;

public class IllegalProductCategoryNameException extends IllegalArgumentException {
    public IllegalProductCategoryNameException() {
        super("Wrong product category name");
    }
}
