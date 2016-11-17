package com.chubb.reporting;

import com.chubb.AbstractGenericTest;
import com.chubb.app.Config;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by ichistruga on 11/3/2016.
 */
@Component
public final class LogReportGenerationTest extends AbstractGenericTest {

    @Autowired
    private ReportGenerator reportGenerator;


    @Test
    public void generateHtmlReport() throws Exception {

        URL testLogURL = Config.class.getResource("/test-log.log");
        if (testLogURL == null) {
            throw new FileNotFoundException("missing test log file '/test-log.log'");
        }

        File logFile = new File(testLogURL.getFile());
        reportGenerator.generate(logFile, 0);

    }
}
