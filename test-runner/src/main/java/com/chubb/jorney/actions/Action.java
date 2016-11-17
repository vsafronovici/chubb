package com.chubb.jorney.actions;

import com.chubb.app.runner.TestRunnerContext;
import com.chubb.exception.SevereException;
import com.chubb.jorney.Verb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by ichistruga on 10/24/2016.
 */

public abstract class Action {

    public static final String ACTION = "Action";
    public static final String RESPONSE = "RESPONSE";

    @Autowired
    protected TestRunnerContext context;

    public abstract Verb getVerb();

    public void execute(String paramName, String paramValue) {
        assertExecutionArguments(paramName, paramValue);
    }

    protected void assertExecutionArguments(String paramName, String paramValue) {
        if (StringUtils.isEmpty(paramName)) {
            throw new SevereException("'paramName' is missing");
        }
        if (StringUtils.isEmpty(paramValue)) {
            throw new SevereException("'paramValue' is missing");
        }
    }


    protected Map<String, String> getCsvData() {
        return context.getStorage().getCsvData();
    }

    protected Map<String, Object> getContextData() {
        return context.getStorage().getContextData();
    }


}
