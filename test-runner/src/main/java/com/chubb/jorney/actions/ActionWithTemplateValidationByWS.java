package com.chubb.jorney.actions;

import com.chubb.app.Config;
import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.response.Response;

import java.io.File;

/**
 * Created by vsafronovici on 11/2/2016.
 */
public abstract class ActionWithTemplateValidationByWS extends ActionWithTemplate {

    protected abstract boolean isSoftly();

    protected abstract void validate(File template);

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);

        File template = getTemplateFile(paramName);
        validate(template);

    }

    @Override
    protected File getTemplateDir() {
        return Config.getResponseTemplatesDir();
    }

    protected Response getResponse() {
        Response response = (Response) getContextData().get(RESPONSE);
        if (response == null) {
            throw new SevereException("Response object not found in storage");
        }
        return response;
    }


}
