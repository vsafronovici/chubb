package com.chubb.jorney.actions;

import com.chubb.exception.ResponseValidationException;
import com.chubb.exception.SevereException;
import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.response.Response;
import com.chubb.rest.adapter.response.RestResponse;
import com.chubb.rest.adapter.response.SoapResponse;
import com.chubb.rest.adapter.util.JsonUtil;
import com.chubb.rest.adapter.util.XmlUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

/**
 * Created by ichistruga on 10/18/2016.
 */
@Service("StoreParam" + Action.ACTION)
public class StoreParamAction extends ActionWithWS {

    private static final String err_message = "Response body doesn't have field path '%s'";

    @Override
    public Verb getVerb() {
        return Verb.StoreParam;
    }

    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);

        final Response response = getResponse();
        String value = null;
        if (Response.REST == response.getType()) {
            JsonNode body = (JsonNode) getResponse().getBody();
            JsonNode node = JsonUtil.findNodeByPath(body, paramValue);
            if (node == null || JsonNodeType.MISSING == node.getNodeType()) {
                throwNodeMissingException(paramValue);
            }

            value = node.asText();

        } else if (Response.SOAP == response.getType()) {
            Element body = (Element) response.getBody();
            Element node = XmlUtil.findNodeByPath(body, paramValue);
            if (node == null) {
                throwNodeMissingException(paramValue);
            }

            value = node.getTextTrim();
        }

        getContextData().put(paramName, value);

    }

    private static void throwNodeMissingException(String filedPath) throws SevereException {
        throw new SevereException(String.format("Response body doesn't have field path '%s'", filedPath));
    }

}
