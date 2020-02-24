package com.bigpay.app.component.exception;

public class NonExistingPathException extends RuntimeException {
    public NonExistingPathException(String message) {
        super(message);
    }
}
