package com.chubb.jorney.actions;

import com.chubb.exception.SevereException;
import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.GenericHttpResponseValidator;
import com.chubb.rest.adapter.util.StringMatcherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateResponseHttpStatusCode" + Action.ACTION)
public class ValidateResponseHttpStatusCodeAction extends ActionWithValidationByWS {

    @Autowired
    private GenericHttpResponseValidator genericHttpResponseValidator;

    @Override
    public Verb getVerb() {
        return Verb.ValidateResponseHttpStatusCode;
    }

    @Override
    protected void validate(String expectedName, String expectedValue) {
        genericHttpResponseValidator.validateStatusCode(getResponse(), Integer.parseInt(expectedValue));
    }

    @Override
    public void assertExecutionArguments(String paramName, String paramValue) {
        if (StringUtils.isEmpty(paramValue)) {
            throw new SevereException("Status code value not provided");
        }
        if (!StringMatcherUtils.matchIntegerValue(paramValue)) {
            throw new SevereException("Status code value provided incorrectly");
        }
    }
}
