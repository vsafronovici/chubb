package com.chubb.exception;

/**
 * This is a subclass for @see ChubbException with reason = VALIDATION.
 *
 * Created by vsafronovici on 10/18/2016.
 */
public abstract class ValidationException extends ChubbException {


    public ValidationException(String message) {
        super(ChubbExceptionReason.VALIDATION, message);
    }

}
