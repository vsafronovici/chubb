package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.RestResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateRestResponseByChildNodesTemplate" + Action.ACTION)
public class ValidateRestResponseByChildNodesTemplateAction extends ActionWithTemplateValidationByWS {

    @Autowired
    private RestResponseValidator restResponseValidator;

    @Override
    public Verb getVerb() {
        return Verb.ValidateRestResponseByChildNodesTemplate;
    }


    @Override
    protected boolean isSoftly() {
        return false;
    }

    @Override
    protected void validate(File template) {
        restResponseValidator.validateByChildNodesTemplate(getResponse(), template, isSoftly());
    }


}
