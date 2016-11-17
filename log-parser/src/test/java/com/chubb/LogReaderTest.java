package com.chubb;

import com.chubb.logparser.Entry.ReportEntry;
import com.chubb.logparser.Reader.LogReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class LogReaderTest {

    @Test
    public void LogReaderTest() throws IOException, ParseException {

        URL testData =  LogReaderTest.class.getClassLoader().getResource("mylog.log");

        ReportEntry logEntries = LogReader.readReportData(testData.getPath());

        Assert.assertNotNull(logEntries);
        Assert.assertTrue(logEntries.getChildren().size() > 0);
    }
}
