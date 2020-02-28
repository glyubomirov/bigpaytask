package com.bigpay.app.component.exception;

/**
 * Exception that is performed if there is no Path between two stations
 *
 * @author ggeorgiev
 *
 */
public class NonExistingPathException extends RuntimeException {
    public NonExistingPathException(String message) {
        super(message);
    }
}
