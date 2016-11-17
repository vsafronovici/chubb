package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.SoapResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateSoapResponseByChildNodesTemplate" + Action.ACTION)
public class ValidateSoapResponseByChildNodesTemplateAction extends ActionWithTemplateValidationByWS {

    @Autowired
    private SoapResponseValidator soapResponseValidator;

    @Override
    public Verb getVerb() {
        return Verb.ValidateSoapResponseByChildNodesTemplate;
    }

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);
    }

    @Override
    protected boolean isSoftly() {
        return false;
    }

    @Override
    protected void validate(File template) {
        soapResponseValidator.validateByChildNodesTemplate(getResponse(), template, isSoftly());
    }


}
