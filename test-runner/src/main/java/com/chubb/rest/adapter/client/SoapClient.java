package com.chubb.rest.adapter.client;

import com.chubb.rest.adapter.request.Request;
import com.chubb.rest.adapter.response.Response;
import com.chubb.rest.adapter.response.SoapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vsafronovici on 11/8/2016.
 */
@Service
public class SoapClient {

    @Autowired
    private RestTemplate restTemplate;


    public Response sendRequest(Request request) {
        HttpEntity<String> entity = new HttpEntity<String>(request.getBody(), request.getHeaders());
        ResponseEntity<String> responseEntity = restTemplate.exchange(request.getUrl(), request.getMethod(), entity, String.class);

        return new SoapResponse(responseEntity);
    }

}
