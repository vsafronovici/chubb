package com.chubb.jorney.actions;

import com.chubb.app.Config;
import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.client.RestJsonClient;
import com.chubb.rest.adapter.connection.Connection;
import com.chubb.rest.adapter.connection.ConnectionFactory;
import com.chubb.rest.adapter.request.Request;
import com.chubb.rest.adapter.request.RequestFactoryREST;
import com.chubb.rest.adapter.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by ichistruga on 10/18/2016.
 */
@Service("SendRestRequest" + Action.ACTION)
public class SendRestRequestAction extends ActionWithTemplate {


    @Autowired
    private RequestFactoryREST requestFactoryREST;

    @Autowired
    private RestJsonClient restJsonClient;

    @Override
    public Verb getVerb() {
        return Verb.SendRestRequest;
    }

    @Override
    protected File getTemplateDir() {
        return Config.getRequestTemplatesDir();
    }

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);

        Connection connection = ConnectionFactory.getConnection(Config.getRestEndPoint());
        Request req = requestFactoryREST.create(connection, getTemplateFile(paramName));
        Response resp = restJsonClient.sendRequest(req);
        context.getStorage().getContextData().put(Action.RESPONSE, resp);
    }

}
