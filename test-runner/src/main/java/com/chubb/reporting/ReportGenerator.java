package com.chubb.reporting;

import com.chubb.app.Config;
import com.chubb.exception.SevereException;
import com.chubb.logparser.Entry.ReportEntry;
import com.chubb.logparser.Reader.LogReader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by ichistruga on 11/3/2016.
 */
@Service
public class ReportGenerator {

    private Configuration configuration;

    public ReportGenerator() throws IOException {
        this(Config.getReportTemplatesDir().getPath());
    }

    public ReportGenerator(String templateDirectory) throws IOException {
        this.configuration = new Configuration(Configuration.VERSION_2_3_23);
        this.configuration.setDirectoryForTemplateLoading(new File(templateDirectory));
    }

    public void generateToFile(Map<String, Object> root, String templateName, String filePath)
            throws IOException, TemplateException {
        String code = generate(root, templateName);
        FileUtils.writeStringToFile(new File(filePath), code, Charset.defaultCharset());
    }

    public void generate(File logFile, long totalExecutionTime) throws Exception {

        if (!logFile.exists()) {
            throw new SevereException(String.format("Could not find log file %s", logFile.getAbsolutePath()));
        }

        ReportEntry report = LogReader.readReportData(logFile.getPath());

        report = ReportEntry.groupByThreadName(report);

        Map<String, Object> root = new HashMap();
        root.put("nodes", report.getChildren());
        root.put("total", totalExecutionTime);

        generateToFile(root, "template.ftl",
                FileUtils.getFile(getReportDir(), "report.html").getPath());

    }

    private String generate(Map<String, Object> root, String templateName) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        try {
            template.process(root, writer);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        writer.close();
        return writer.toString();
    }

    private static File getReportDir() {
        File reportDir = FileUtils.getFile(new File(Config.getWorkDir()), "reports");
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
        return reportDir;
    }

}

