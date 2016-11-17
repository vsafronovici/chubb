package com.chubb.rest.adapter.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.StringReader;

/**
 * Created by vsafronovici on 11/8/2016.
 */
public class XmlUtil {

    private static final String DOM4J_PATH_DELIMITER = "/";


    public static Document loadDocument(String xml) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(new StringReader(xml));
    }

    public static Document loadDocument(File xmlFile) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(xmlFile);
    }

    public static Element findNodeByPath(Element node, String path) {
        String xPath = "//".concat(path.replaceAll("\\.", DOM4J_PATH_DELIMITER));
        return (Element) node.selectSingleNode(xPath);
    }


}
