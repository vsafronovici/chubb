package com.chubb.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by vsafronovici on 10/19/2016.
 */
public class PropertyUtils {

    public static Map<String, String> getProperties(File file) throws IOException {

        if (!file.exists()) {
            throw new FileNotFoundException("Could not find file: " + file.getAbsolutePath());
        }

        Map<String, String> properties = null;
        InputStream inputStream = null;

        try {
            inputStream = FileUtils.openInputStream(file);
            Properties prop = new Properties();
            prop.load(inputStream);
            properties = new HashMap<>((Map) prop);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return properties;
    }

    public static Map<String, String> getProperties(String filePath) throws IOException {
        return getProperties(new File(filePath));
    }

}
