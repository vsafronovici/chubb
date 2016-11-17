package com.chubb.rest.adapter.response;

import com.chubb.exception.ResponseValidationException;
import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.util.XmlUtil;
import com.chubb.util.FileUtil;
import com.chubb.util.TemplateInjector;
import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vsafronovici on 10/19/2016.
 */
@Service
public class SoapResponseValidator {

    @Autowired
    private TemplateInjector injector;

    /**
     * Method validates the SOAP response Body compared to expected response generated from responseTemplateFile.
     * Each node from expected response is compared to node from response.
     *
     * @param response             Response
     * @param responseTemplateFile File
     * @throws ResponseValidationException
     */
    public void validateByTemplate(Response response, File responseTemplateFile) throws ResponseValidationException {
        Element expectedResponse = getExpectedResponse(responseTemplateFile);
        validateBody(expectedResponse, (Element) response.getBody());
    }


    /**
     * Method validates the SOAP response Body compared to expected response generated from responseTemplateFile.
     * If in expected response a node is missing or the node value is "" - empty string then this node is skipped from validation.
     *
     * @param response             Response
     * @param responseTemplateFile File
     * @throws ResponseValidationException
     */
    public void validateByTemplateSoftly(Response response, File responseTemplateFile) throws ResponseValidationException {
        Element expectedResponse = getExpectedResponse(responseTemplateFile);
        validateBodySoftly(expectedResponse, (Element) response.getBody());
    }

    /**
     * Method validates the SOAP response Body compared to expected response Body child nodes from responseTemplateFile.
     * If softly = true then if in expected response a node is missing or the node value is "" - empty string then this node is skipped from validation.
     *
     * @param response               Response
     * @param childNodesTemplateFile File
     * @param softly                 boolean
     * @throws ResponseValidationException
     */
    public void validateByChildNodesTemplate(Response response, File childNodesTemplateFile, boolean softly) throws ResponseValidationException {

        Element expectedBodyChildNode = getExpectedResponse(childNodesTemplateFile);
        Element bodyResponse = (Element) response.getBody();

        Element responseChildNode = (Element) bodyResponse.selectSingleNode(String.format("//%s", expectedBodyChildNode.getName()));

        if (responseChildNode == null) {
            throw new ResponseValidationException(String.format("Response body doesn't have field '%s'", expectedBodyChildNode.getName()));
        } else {
            if (softly) {
                validateBodySoftly(expectedBodyChildNode, responseChildNode);
            } else {
                validateBody(expectedBodyChildNode, responseChildNode);
            }
        }

    }


    /**
     * Method validates the SOAP response Body by field path (e.g. user.address.street).
     *
     * @param response      Response
     * @param fieldPath     String
     * @param expectedValue String
     * @throws ResponseValidationException
     */
    public void validateByPath(Response response, String fieldPath, String expectedValue) throws ResponseValidationException {
        Element body = (Element) response.getBody();
        Element node = XmlUtil.findNodeByPath(body, fieldPath);
        if (node == null) {
            throw new ResponseValidationException(String.format("Response body doesn't have field path '%s'", fieldPath));
        }

        if (!expectedValue.equals(node.getTextTrim())) {
            throw new ResponseValidationException(String.format("Field path '%s' has incorrect value '%s', but expected '%s'", fieldPath, node.getTextTrim(), expectedValue));
        }

    }


    private Element getExpectedResponse(File responseTemplateFile) {
        String responseTemplateBody = FileUtil.readFileToString(responseTemplateFile);
        String expectedResponseBody = injector.inject(responseTemplateBody);
        try {
            Document doc = XmlUtil.loadDocument(expectedResponseBody);
            return doc.getRootElement();
        } catch (DocumentException e) {
            throw new SevereException(String.format("Invalid XML format for file '%s'", responseTemplateFile.getAbsolutePath()));
        }
    }


    private void validateBody(Element expectedNode, Element respNode) {

        final List<String> expectedFieldNames = getNodeFieldNames(expectedNode);
        final List<String> respFieldNames = getNodeFieldNames(respNode);

        Collection<String> disjunction = CollectionUtils.disjunction(expectedFieldNames, respFieldNames);

        if (disjunction.isEmpty()) { /* happy path */
            expectedFieldNames.forEach(childFieldName -> {
                Element expectedChildNode = expectedNode.element(childFieldName);
                Element respChildNode = respNode.element(childFieldName);

                if (expectedChildNode.isTextOnly() && !expectedChildNode.getTextTrim().equals(respChildNode.getTextTrim())) {
                    throw new ResponseValidationException(String.format("Response body has wrong value for field '%s'", respChildNode.getPath()));
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


    private void validateBodySoftly(Element expectedNode, Element respNode) {
        final List<String> expectedFieldNames = getNodeFieldNames(expectedNode);
        final List<String> respFieldNames = getNodeFieldNames(respNode);

        if (respFieldNames.containsAll(expectedFieldNames)) { /* happy path */
            expectedFieldNames.forEach(childFieldName -> {
                Element expectedChildNode = expectedNode.element(childFieldName);
                Element respChildNode = respNode.element(childFieldName);

                if (expectedChildNode.isTextOnly() && !StringUtils.isEmpty(expectedChildNode.getTextTrim())
                        && !expectedChildNode.getTextTrim().equals(respChildNode.getTextTrim())) {
                    throw new ResponseValidationException(String.format("Response body has wrong value for field '%s'", respChildNode.getPath()));
                }

                validateBodySoftly(expectedChildNode, respChildNode);

            });
        } else { /* unhappy path */
            expectedFieldNames.removeAll(respFieldNames);
            throw new ResponseValidationException(String.format("Expected fields %s are missing from response body", expectedFieldNames));
        }
    }

    private List<String> getNodeFieldNames(Element node) {
        final List<String> fieldNames = new ArrayList<>();

        node.elements().forEach(o -> {
            Element el = (Element) o;
            if (Node.ELEMENT_NODE == el.getNodeType()) {
                fieldNames.add(el.getName());
            }
        });

        return fieldNames;
    }

}
