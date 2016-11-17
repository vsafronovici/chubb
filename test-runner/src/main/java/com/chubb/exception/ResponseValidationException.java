package com.chubb.exception;

/**
 * This is subclass for @see ValidationException that should be thrown when REST, SOAP response fails validation rules.
 *
 * Created by vsafronovici on 10/18/2016.
 */
public class ResponseValidationException extends ValidationException {

    public ResponseValidationException(String message) {
        super(message);
    }
}
