package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.SoapResponseValidator;
import com.chubb.util.TemplateInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateSoapResponseByPath" + Action.ACTION)
public class ValidateSoapResponseByPathAction extends ActionWithValidationByWS {

    @Autowired
    private SoapResponseValidator soapResponseValidator;

    @Autowired
    private TemplateInjector injector;

    @Override
    public Verb getVerb() {
        return Verb.ValidateSoapResponseByPath;
    }

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);
    }

    @Override
    protected void validate(String expectedName, String expectedValue) {
        expectedValue = injector.inject(expectedValue);
        soapResponseValidator.validateByPath(getResponse(), expectedName, expectedValue);
    }
}
