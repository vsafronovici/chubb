package com.chubb.exception;

/**
 * This is a RuntimeException that is parent for another exceptions that the application should throw when running.
 *
 * Created by vsafronovici on 10/12/2016.
 */
public class ChubbException extends RuntimeException {

    private final ChubbExceptionReason reason;

    public ChubbException(ChubbExceptionReason reason, String message) {
        this(reason, message, null);
    }

    public ChubbException(ChubbExceptionReason reason, String message, Throwable e) {
        super(message, e);
        this.reason = reason;
    }

    /**
     *
     * @return the reason of exception
     * @see ChubbExceptionReason
     */
    public ChubbExceptionReason getReason() {
        return reason;
    }
}
