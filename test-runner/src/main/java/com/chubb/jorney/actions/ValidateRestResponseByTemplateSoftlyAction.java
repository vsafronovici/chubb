package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import org.springframework.stereotype.Service;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateRestResponseByTemplateSoftly" + Action.ACTION)
public class ValidateRestResponseByTemplateSoftlyAction extends ValidateRestResponseByTemplateAction {

    @Override
    public Verb getVerb() {
        return Verb.ValidateRestResponseByTemplateSoftly;
    }

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);
    }

    @Override
    protected boolean isSoftly() {
        return true;
    }
}
