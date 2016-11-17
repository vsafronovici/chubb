package com.chubb.rest.adapter.util;

import com.chubb.exception.SevereException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by vsafronovici on 10/11/2016.
 */
public final  class JsonUtil {

    private static final String JACKSON_PATH_DELIMITER = "/";

    private JsonUtil() {
    }

    public static JsonNode loadFromFile(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist: " + file.getAbsolutePath());
        }
        FileInputStream fis = new FileInputStream(file);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.reader().readTree(fis);
        IOUtils.closeQuietly(fis);

        return node;
    }

    public static JsonNode loadFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        return loadFromFile(file);
    }

    public static JsonNode convert(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.reader().readTree(json);
        return jsonNode;
    }

    public static JsonNode convert(String json, String errMessage) throws SevereException {
        try {
            return convert(json);
        } catch (IOException e) {
            throw new SevereException(errMessage);
        }
    }

    public static JsonNode findNodeByPath(JsonNode jsonNode, String path) {
        String jacksonPath = JACKSON_PATH_DELIMITER.concat(path.replaceAll("\\.", JACKSON_PATH_DELIMITER));
        return jsonNode.at(jacksonPath);
    }




}
