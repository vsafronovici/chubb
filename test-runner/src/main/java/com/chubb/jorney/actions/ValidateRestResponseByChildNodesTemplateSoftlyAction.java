package com.chubb.jorney.actions;

import com.chubb.jorney.Verb;
import org.springframework.stereotype.Service;

/**
 * Created by vsafronovici on 10/25/2016.
 */
@Service("ValidateRestResponseByChildNodesTemplateSoftly" + Action.ACTION)
public class ValidateRestResponseByChildNodesTemplateSoftlyAction extends ValidateRestResponseByChildNodesTemplateAction {

    @Override
    public Verb getVerb() {
        return Verb.ValidateRestResponseByChildNodesTemplateSoftly;
    }

    @Override
    protected boolean isSoftly() {
        return true;
    }
}
