package com.chubb.rest.adapter.request;

import com.chubb.rest.adapter.connection.Connection;
import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.util.JsonUtil;
import com.chubb.util.FileUtil;
import com.chubb.util.TemplateInjector;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by vsafronovici on 10/11/2016.
 */
@Service
public final class RequestFactoryREST {

    @Autowired
    private TemplateInjector injector;

    public Request create(Connection connection, File requestTemplateFile) {

        String requestTemplateAsString = FileUtil.readFileToString(requestTemplateFile);
        String requestAsString = injector.inject(requestTemplateAsString);

        JsonNode requestNode = JsonUtil.convert(requestAsString, "Invalid template:" + requestTemplateFile);
        String resource = JsonUtil.findNodeByPath(requestNode, "request.resource").asText();
        final String url = connection.getServer().concat(resource);
        final String method = JsonUtil.findNodeByPath(requestNode, "request.method").asText();

        final HttpHeaders headers = new HttpHeaders();
        JsonNode headersNode = JsonUtil.findNodeByPath(requestNode, "request.headers");
        if (!(headersNode instanceof MissingNode)) {
            Iterator<Map.Entry<String, JsonNode>> it = headersNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                headers.add(entry.getKey(), entry.getValue().asText());
            }
        }

        String requestTemplateBodyFileName = JsonUtil.findNodeByPath(requestNode, "request.body").textValue();
        String body = null;
        if (!StringUtils.isEmpty(requestTemplateBodyFileName)) {
            File requestTemplateBodyFile = FileUtil.getRequestTemplateFile(requestTemplateBodyFileName);
            String requestTemplateBody = FileUtil.readFileToString(requestTemplateBodyFile);

            requestTemplateBody = injector.inject(requestTemplateBody);

            body = requestTemplateBody;
        }

        Request request = new Request();
        request.setHeaders(headers);
        request.setUrl(url);
        request.setMethod(HttpMethod.resolve(method));
        request.setBody(body);


        return request;
    }
}
