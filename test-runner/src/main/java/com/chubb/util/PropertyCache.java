package com.chubb.util;

import com.chubb.exception.CriticalException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by ichistruga on 10/11/2016.
 */
public class PropertyCache {

    private final static String PROPERTY_FILE = "/config.properties";

    private static Map<String, String> properties;

    private static synchronized Map<String, String> getProperties() {
        if (properties == null) {
            URL url = PropertyCache.class.getResource(PROPERTY_FILE);
            File file = new File(url.getFile());
            try {
                properties = PropertyUtils.getProperties(file);
            } catch (Exception e) {
                throw new CriticalException(e.getMessage(), e);
            }
        }
        return properties;
    }

    public static String getProperty(String key) {
        return getProperties().get(key);
    }
}
