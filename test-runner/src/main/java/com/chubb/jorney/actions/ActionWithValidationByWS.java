package com.chubb.jorney.actions;

/**
 * Created by vsafronovici on 11/2/2016.
 */
public abstract class ActionWithValidationByWS extends ActionWithWS {

    protected abstract void validate(String expectedName, String expectedValue);

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);
        validate(paramName, paramValue);
    }
}
