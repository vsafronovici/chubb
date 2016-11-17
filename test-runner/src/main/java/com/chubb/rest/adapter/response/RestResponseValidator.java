package com.chubb.rest.adapter.response;

import com.chubb.exception.ResponseValidationException;
import com.chubb.rest.adapter.util.JsonUtil;
import com.chubb.util.FileUtil;
import com.chubb.util.TemplateInjector;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;

/**
 * Created by vsafronovici on 10/19/2016.
 */
@Service
public class RestResponseValidator {

    private static final EnumSet<JsonNodeType> NODE_TYPES_TO_COMPARE = EnumSet.of(JsonNodeType.ARRAY, JsonNodeType.STRING, JsonNodeType.BOOLEAN, JsonNodeType.NUMBER);
    private static final EnumSet<JsonNodeType> NODE_TYPES_TO_COMPARE_SOFTLY = EnumSet.of(JsonNodeType.ARRAY, JsonNodeType.BOOLEAN, JsonNodeType.NUMBER);

    @Autowired
    private TemplateInjector injector;

    /**
     * Method validates the REST response Body compared to expected response generated from responseTemplateFile.
     * Each node from expected response is compared to node from response.
     *
     * @param response Response
     * @param responseTemplateFile File
     * @throws ResponseValidationException
     */
    public void validateByTemplate(Response response, File responseTemplateFile) throws ResponseValidationException {
        JsonNode expectedResponseBodyAsNode = getExpectedResponseBodyAsNode(responseTemplateFile);
        validateBody(expectedResponseBodyAsNode, (JsonNode) response.getBody());
    }


    /**
     * Method validates the REST response Body compared to expected response generated from responseTemplateFile.
     * If in expected response a node is missing or the node value is "" - empty string then this node is skipped from validation.
     *
     * @param response Response
     * @param responseTemplateFile File
     * @throws ResponseValidationException
     */
    public void validateByTemplateSoftly(Response response, File responseTemplateFile) throws ResponseValidationException {
        JsonNode expectedResponseBodyAsNode = getExpectedResponseBodyAsNode(responseTemplateFile);
        validateBodySoftly(expectedResponseBodyAsNode, (JsonNode) response.getBody());
    }

    /**
     * Method validates the REST response Body compared to expected response Body child nodes from responseTemplateFile.
     * If softly = true then if in expected response a node is missing or the node value is "" - empty string then this node is skipped from validation.
     * @param response Response
     * @param childNodesTemplateFile File
     * @param softly boolean
     * @throws ResponseValidationException
     */
    public void validateByChildNodesTemplate(Response response, File childNodesTemplateFile, boolean softly) throws ResponseValidationException {
        JsonNode expectedBodyChildNodes = getExpectedResponseBodyAsNode(childNodesTemplateFile);
        final JsonNode body = (JsonNode) response.getBody();

        Iterator<Map.Entry<String, JsonNode>> it = expectedBodyChildNodes.fields();
        it.forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            JsonNode expectedChildNode = entry.getValue();
            JsonNode responseChildNode = body.findValue(fieldName);

            if (responseChildNode == null) {
                throw new ResponseValidationException(String.format("Response body doesn't have field '%s'", fieldName));
            }

            if (softly) {
                validateBodySoftly(expectedChildNode, responseChildNode);
            } else {
                validateBody(expectedChildNode, responseChildNode);
            }


        });

    }


    /**
     * Method validates the REST response Body by field path (e.g. user.address.street).
     *
     * @param response Response
     * @param fieldPath String
     * @param expectedValue String
     * @throws ResponseValidationException
     */
    public void validateByPath(Response response, String fieldPath, String expectedValue) throws ResponseValidationException {
        JsonNode body = (JsonNode) response.getBody();
        JsonNode node = JsonUtil.findNodeByPath(body, fieldPath);
        if (node == null || JsonNodeType.MISSING == node.getNodeType()) {
            throw new ResponseValidationException(String.format("Response body doesn't have field path '%s'", fieldPath));
        }

        String value = node.asText();
        if (!expectedValue.equals(value)) {
            throw new ResponseValidationException(String.format("Field path '%s' has incorrect value '%s', but expected '%s'", fieldPath, value, expectedValue));
        }

    }


    private JsonNode getExpectedResponseBodyAsNode(File responseTemplateBodyFile) {
        String responseTemplateBody = FileUtil.readFileToString(responseTemplateBodyFile);
        String expectedResponseBody = injector.inject(responseTemplateBody);
        return JsonUtil.convert(expectedResponseBody, "Invalid template body:" + responseTemplateBodyFile.getAbsolutePath());
    }


    private void validateBody(final JsonNode expectedNode, final JsonNode respNode) {
        final List<String> expectedFieldNames = IteratorUtils.toList(expectedNode.fieldNames());
        final List<String> respFieldNames = IteratorUtils.toList(respNode.fieldNames());
        Collection<String> disjunction = CollectionUtils.disjunction(expectedFieldNames, respFieldNames);

        if (disjunction.isEmpty()) { /* happy path */
            expectedFieldNames.forEach(childFieldName -> {
                JsonNode expectedChildNode = expectedNode.get(childFieldName);
                JsonNode respChildNode = respNode.get(childFieldName);

                if (expectedChildNode.getNodeType() != respChildNode.getNodeType() ||
                        NODE_TYPES_TO_COMPARE.contains(expectedChildNode.getNodeType()) && !expectedChildNode.equals(respChildNode)) {
                    throw new ResponseValidationException(String.format("Response body has wrong value for field '%s'", childFieldName));
                }

                validateBody(expectedChildNode, respChildNode);

            });

        } else { /* unhappy path */
            disjunction.forEach(childFieldName -> {
                if (expectedFieldNames.contains(childFieldName)) {
                    throw new ResponseValidationException(String.format("Expected field '%s' is missing from response body", childFieldName));
                } else {
                    throw new ResponseValidationException(String.format("Response body contains field '%s' that is not expected", childFieldName));
                }
            });
        }
    }


    private void validateBodySoftly(final JsonNode expectedNode, final JsonNode respNode) {
        final List<String> expectedFieldNames = IteratorUtils.toList(expectedNode.fieldNames());
        final List<String> respFieldNames = IteratorUtils.toList(respNode.fieldNames());

        if (respFieldNames.containsAll(expectedFieldNames)) { /* happy path */
            expectedFieldNames.forEach(childFieldName -> {
                JsonNode expectedChildNode = expectedNode.get(childFieldName);
                JsonNode respChildNode = respNode.get(childFieldName);

                if (NODE_TYPES_TO_COMPARE_SOFTLY.contains(expectedChildNode.getNodeType()) ||
                        JsonNodeType.STRING == expectedChildNode.getNodeType() && !StringUtils.isEmpty(expectedChildNode.asText())) {
                    if (!expectedChildNode.equals(respChildNode)) {
                        throw new ResponseValidationException(String.format("Response body has wrong value for field '%s'", childFieldName));
                    }
                }

                validateBodySoftly(expectedChildNode, respChildNode);

            });
        } else { /* unhappy path */
            expectedFieldNames.removeAll(respFieldNames);
            //String missingFields = StringUtils.arrayToCommaDelimitedString();
            throw new ResponseValidationException(String.format("Expected fields %s are missing from response body", expectedFieldNames));
        }
    }

}
