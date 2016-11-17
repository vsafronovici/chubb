package com.chubb.exception;

import com.chubb.jorney.Verb;

/**
 * This is a subclass for @see ChubbException and should wrap another exceptions when an @see Action fails.
 *
 * Created by vsafronovici on 10/18/2016.
 */
public class ActionException extends ChubbException {

    private final Verb verb;

    public ActionException(Verb verb, ChubbException ex) {
        this(verb, ex.getReason(), ex.getMessage(), ex.getCause());
    }

    public ActionException(Verb verb, ChubbExceptionReason reason, String message) {
        this(verb, reason, message, null);
    }

    public ActionException(Verb verb, ChubbExceptionReason reason, String message, Throwable th) {
        super(reason, buildExceptionMessage(verb, message), th);
        this.verb = verb;
    }

    public Verb getVerb() {
        return verb;
    }

    private static String buildExceptionMessage(Verb verb, String message) {
        return String.format("Action:%s: %s", verb, message);
    }

}
