package com.chubb.rest.adapter.response;

import com.chubb.exception.ResponseValidationException;
import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.util.JsonUtil;
import com.chubb.util.FileUtil;
import com.chubb.util.TemplateInjector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by vsafronovici on 10/18/2016.
 */
@Service
public class GenericHttpResponseValidator {

    @Autowired
    private TemplateInjector injector;


    public void validateStatusCode(Response response, int expectedStatusCode) throws ResponseValidationException {
        if (response.getStatusCode() != expectedStatusCode) {
            String msg = String.format("Status code = %d, but expected is %d", response.getStatusCode(), expectedStatusCode);
            throw new ResponseValidationException(msg);
        }
    }

    public void validateHttpStatus(Response response, String expectedHttpStatus) throws ResponseValidationException {
        String httpStatus = response.getHttpStatus();
        if (!httpStatus.equalsIgnoreCase(expectedHttpStatus.trim())) {
            String msg = String.format("Http status = '%s', but expected is '%s'", httpStatus, expectedHttpStatus);
            throw new ResponseValidationException(msg);
        }
    }

    public void validateHeaderValues(Response response, String headerName, String expectedHeaderValue, boolean softly) throws ResponseValidationException {
        String headerValue = response.getHeaders().get(headerName);
        if (headerValue == null) {
            throw new ResponseValidationException(String.format("Missing header '%s'", headerName));
        }

        if (softly && StringUtils.isEmpty(expectedHeaderValue)) {
            return;
        }

        if (!expectedHeaderValue.trim().equalsIgnoreCase(headerValue.trim())) {
            String msg = String.format("Header value for name '%s' is '%s', but expected is '%s'", headerName, headerValue, expectedHeaderValue);
            throw new ResponseValidationException(msg);
        }
    }

    public void validateHeaderValues(Response response, String headerName, String expectedHeaderValue) throws ResponseValidationException {
        validateHeaderValues(response, headerName, expectedHeaderValue, false);
    }

    public void validateStatusAndHeaderByTemplate(Response response, File templateFile, boolean softly) throws ResponseValidationException {
        String templateAsString = FileUtil.readFileToString(templateFile);
        String expectedDataAsString = injector.inject(templateAsString);
        JsonNode expectedNode = JsonUtil.convert(expectedDataAsString, "Invalid template body:" + templateFile.getAbsolutePath());

        /* Validate status code */
        String expectedStatusCode = JsonUtil.findNodeByPath(expectedNode, "response.statusCode").asText(null);
        int expectedStatusCodeInt = 0;
        if (!StringUtils.isEmpty(expectedStatusCode)) {
            try {
                expectedStatusCodeInt = Integer.parseInt(expectedStatusCode);
            } catch (NumberFormatException e) {
                throw new SevereException(String.format("Status code has invalid integer format '%s' in '%s'", expectedStatusCode, templateFile.getAbsolutePath()));
            }
        }
        if (!softly && StringUtils.isEmpty(expectedStatusCode)) {
            throw new ResponseValidationException(String.format("Status code node missing from '%s'", templateFile.getAbsolutePath()));
        }
        if (!softly || softly && !StringUtils.isEmpty(expectedStatusCode)) {
            this.validateStatusCode(response, expectedStatusCodeInt);
        }

        /* Validate header */
        JsonNode expectedHeadersNode = JsonUtil.findNodeByPath(expectedNode, "response.headers");
        EnumSet<JsonNodeType> ignoreNodeTypes = EnumSet.of(JsonNodeType.MISSING, JsonNodeType.NULL);
        if (!ignoreNodeTypes.contains(expectedHeadersNode.getNodeType())) {
            Iterator<Map.Entry<String, JsonNode>> it = expectedHeadersNode.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                this.validateHeaderValues(response, entry.getKey(), entry.getValue().asText(), softly);
            }
        }

    }


    public void validateBody(Response response, String expectedBody) throws ResponseValidationException {
        String trimmed1 = StringUtils.trimAllWhitespace(response.getBodyAsString());
        String trimmed2 = StringUtils.trimAllWhitespace(expectedBody);

        if (!trimmed1.equalsIgnoreCase(trimmed2)) {
            throw new ResponseValidationException("Response body is not as expected");
        }
    }

    public void validateBodyContains(Response response, String expectedContainingString) throws ResponseValidationException {
        String body = response.getBodyAsString();
        if (!body.contains(expectedContainingString.trim())) {
            String msg = String.format("Response body doesn't contain expected text '%s'", expectedContainingString);
            throw new ResponseValidationException(msg);
        }
    }

}