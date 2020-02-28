package com.bigpay.app.component.exception;


/**
 * Exception that is performed if action can not be performed because of incorrect action on th road map
 *
 * @author ggeorgiev
 *
 */
public class WrongRoadMapStateException extends RuntimeException {
    public WrongRoadMapStateException(String message) {
        super(message);
    }
}
