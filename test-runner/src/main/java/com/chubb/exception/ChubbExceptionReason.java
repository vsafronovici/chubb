package com.chubb.exception;

/**
 * This enum defines the 2 reasons why application could throw an exception
 *
 * Created by vsafronovici on 10/27/2016.
 */
public enum ChubbExceptionReason {

    /**
     * When data provided for running a test is invalid. E.g. a file is missing, bad JSON or XML format provided, etc.
     */
    SEVERE,

    /**
     * When expected data does not correspond to data received.
     */
    VALIDATION

}
