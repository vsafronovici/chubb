package com.chubb.rest.adapter.request;

import com.chubb.rest.adapter.connection.Connection;
import com.chubb.util.FileUtil;
import com.chubb.util.TemplateInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by vsafronovici on 10/11/2016.
 */
@Service
public final class RequestFactorySOAP {

    @Autowired
    private TemplateInjector injector;

    public Request create(Connection connection, File requestTemplateFile) {

        String requestTemplateBody = FileUtil.readFileToString(requestTemplateFile);
        requestTemplateBody = injector.inject(requestTemplateBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        Request request = new Request();
        request.setHeaders(headers);
        request.setUrl(connection.getServer());
        request.setMethod(HttpMethod.POST);
        request.setBody(requestTemplateBody);

        return request;
    }

}
