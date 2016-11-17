package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import org.springframework.stereotype.Service;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateResponseHttpStatusAndHeaderByTemplateSoftly" + Action.ACTION)
public class ValidateResponseHttpStatusAndHeaderByTemplateSoftlyAction extends ValidateResponseHttpStatusAndHeaderByTemplateAction {

    @Override
    public Verb getVerb() {
        return Verb.ValidateResponseHttpStatusAndHeaderByTemplateSoftly;
    }

    @Override
    protected boolean isSoftly() {
        return true;
    }


}
