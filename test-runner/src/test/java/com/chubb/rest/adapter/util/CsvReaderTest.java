package com.chubb.rest.adapter.util;

import com.chubb.util.CsvReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ichistruga on 10/17/2016.
 */
public class CsvReaderTest {


    @Test
    public void TestReadCSV() throws IOException {
        URL url = CsvReader.class.getResource("/data.csv");

        List<Map<String, String>> data = CsvReader.readCSV(url.getPath());

        Assert.assertEquals(data.size(), 4);
        Assert.assertEquals(data.get(0).size(),10);

    }

    @Test
    public void TestWriteCSV() throws IOException {

        Map<String,String> line1 =new HashMap<String,String>() {
            {
                this.put("Name", "Igor");
                this.put("Company", "Endava");
            }
        } ;
        Map<String,String> line2 =new HashMap<String,String>() {
            {
                this.put("Name", "Aliona");
                this.put("Company", "CTAS");
            }
        } ;
        List<Map<String, String>> data = new ArrayList<Map<String,String>>();
        data.add(line1);
        data.add(line2);

        String result = CsvReader.writeCSV(data);

        System.out.print(result);
        Assert.assertTrue(result.length() != 0);

    }

}
