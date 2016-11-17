package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.GenericHttpResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateResponseHttpHeaderValues" + Action.ACTION)
public class ValidateResponseHttpHeaderAction extends ActionWithValidationByWS {

    @Autowired
    private GenericHttpResponseValidator genericHttpResponseValidator;

    @Override
    public Verb getVerb() {
        return Verb.ValidateResponseHttpHeaderValues;
    }

    @Override
    protected void validate(String expectedName, String expectedValue) {
        genericHttpResponseValidator.validateHeaderValues(getResponse(), expectedName, expectedValue);
    }

}
