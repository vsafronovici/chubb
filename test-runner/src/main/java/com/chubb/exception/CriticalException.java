package com.chubb.exception;

/**
 * This is a RuntimeException that is thrown only during the init stage of the application
 * and should not be thrown when application is running.
 *
 * Created by vsafronovici on 10/12/2016.
 */
public class CriticalException extends RuntimeException {

    public CriticalException(String message) {
        super(message);
    }

    public CriticalException(String message, Throwable e) {
        super(message, e);
    }
}
