package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.RestResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateRestResponseByTemplate" + Action.ACTION)
public class ValidateRestResponseByTemplateAction extends ActionWithTemplateValidationByWS {

    @Autowired
    private RestResponseValidator responseValidator;

    @Override
    public Verb getVerb() {
        return Verb.ValidateRestResponseByTemplate;
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
        if (isSoftly()) {
            responseValidator.validateByTemplateSoftly(getResponse(), template);
        } else {
            responseValidator.validateByTemplate(getResponse(), template);
        }
    }


}
