package com.chubb.jorney.actions;

import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.response.Response;

/**
 * Created by vsafronovici on 11/2/2016.
 */
public abstract class ActionWithWS extends Action {


    protected Response getResponse() {
        Response response = (Response) getContextData().get(RESPONSE);
        if (response == null) {
            throw new SevereException("Response object not found in storage");
        }
        return response;
    }

}
