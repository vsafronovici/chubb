package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.GenericHttpResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateResponseHttpStatusAndHeaderByTemplate" + Action.ACTION)
public class ValidateResponseHttpStatusAndHeaderByTemplateAction extends ActionWithTemplateValidationByWS {

    @Autowired
    private GenericHttpResponseValidator genericHttpResponseValidator;

    @Override
    public Verb getVerb() {
        return Verb.ValidateResponseHttpStatusAndHeaderByTemplate;
    }

    @Override
    protected boolean isSoftly() {
        return false;
    }

    @Override
    protected void validate(File template) {
        genericHttpResponseValidator.validateStatusAndHeaderByTemplate(getResponse(), template, isSoftly());
    }


}
