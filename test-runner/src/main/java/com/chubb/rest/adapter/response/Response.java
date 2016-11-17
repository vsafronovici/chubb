package com.chubb.rest.adapter.response;


import java.util.Map;

/**
 * Created by ichistruga on 10/11/2016.
 */
public interface Response {

    int REST = 1;
    int SOAP = 2;


    int getType();

    int getStatusCode();

    String getHttpStatus();

    Map<String, String> getHeaders();

    Object getBody();

    String getBodyAsString();

}
