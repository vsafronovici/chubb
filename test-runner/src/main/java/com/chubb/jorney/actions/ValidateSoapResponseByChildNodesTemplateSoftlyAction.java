package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.RestResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateSoapResponseByChildNodesTemplateSoftly" + Action.ACTION)
public class ValidateSoapResponseByChildNodesTemplateSoftlyAction extends ValidateSoapResponseByChildNodesTemplateAction {

    @Override
    public Verb getVerb() {
        return Verb.ValidateSoapResponseByChildNodesTemplateSoftly;
    }


    @Override
    protected boolean isSoftly() {
        return true;
    }


}
