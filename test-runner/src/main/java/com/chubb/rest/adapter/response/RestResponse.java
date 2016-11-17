package com.chubb.rest.adapter.response;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by vsafronovici on 10/19/2016.
 */
public class RestResponse implements Response {

    private final ResponseEntity<JsonNode> responseEntity;

    public RestResponse(ResponseEntity<JsonNode> responseEntity) {
        this.responseEntity = responseEntity;
    }

    @Override
    public int getType() {
        return REST;
    }

    @Override
    public int getStatusCode() {
        return responseEntity.getStatusCodeValue();
    }

    @Override
    public String getHttpStatus() {
        return responseEntity.getStatusCode().getReasonPhrase();
    }

    @Override
    public Map<String, String> getHeaders() {
        return responseEntity.getHeaders().toSingleValueMap();
    }

    @Override
    public JsonNode getBody() {
        return responseEntity.getBody();
    }

    @Override
    public String getBodyAsString() {
        return getBody().toString();
    }

}
