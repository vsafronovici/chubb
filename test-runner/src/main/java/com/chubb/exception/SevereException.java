package com.chubb.exception;

/**
 * This is a subclass for @see ChubbException with reason = SEVERE.
 *
 * Created by vsafronovici on 10/12/2016.
 */
public class SevereException extends ChubbException {


    public SevereException(String message) {
        this(message, null);
    }

    public SevereException(String message, Throwable e) {
        super(ChubbExceptionReason.SEVERE, message, e);
    }

}
