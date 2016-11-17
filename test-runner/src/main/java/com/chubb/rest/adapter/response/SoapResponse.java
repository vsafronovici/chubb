package com.chubb.rest.adapter.response;

import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.http.ResponseEntity;

import java.io.StringReader;
import java.util.Map;

/**
 * Created by vsafronovici on 10/19/2016.
 */
public class SoapResponse implements Response {

    private final ResponseEntity<String> responseEntity;
    private final Element body;

    public SoapResponse(ResponseEntity<String> responseEntity) {
        this.responseEntity = responseEntity;

        try {
            Document doc = XmlUtil.loadDocument(responseEntity.getBody());
            this.body = doc.getRootElement();
        } catch (DocumentException e) {
            throw new SevereException("Invalid XML format when receiving SOAP response");
        }
    }

    @Override
    public int getType() {
        return SOAP;
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
    public Element getBody() {
        return this.body;
    }

    @Override
    public String getBodyAsString() {
        return responseEntity.getBody();
    }



}
