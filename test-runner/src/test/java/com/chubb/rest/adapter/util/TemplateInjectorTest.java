package com.chubb.rest.adapter.util;

import com.chubb.AbstractGenericTest;
import com.chubb.jorney.Verb;
import com.chubb.storage.Storage;
import com.chubb.util.TemplateInjector;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ichistruga on 10/17/2016.
 */
public class TemplateInjectorTest extends AbstractGenericTest {

    @Autowired
    TemplateInjector injector;

    @Autowired
    private Storage storage;

    @Test
    public void Test1() throws Exception {

        File file = new File(TemplateInjectorTest.class.getResource("/data.json").getPath());
        String inputData = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        storage.getContextData().put("name", "Igor");
        storage.getContextData().put("company", "Endava");

        storage.getCsvData().put("name", "Vitalie");
        storage.getContextData().put("company", "Compudava");


        String result = injector.inject(inputData);
        System.out.print(result);

        Assert.assertNotNull(result);
        assertTrue(result.contains("\"name\": \"Vitalie\""));
        assertTrue(result.contains("\"company\": \"Compudava\""));
    }
}