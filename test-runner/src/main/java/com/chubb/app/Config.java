package com.chubb.app;

import com.chubb.exception.CriticalException;
import com.chubb.util.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Class that loads the config data needed for application working properly.
 * <p>
 * Created by vsafronovici on 10/17/2016.
 */
public final class Config {

    private static final String ERR_SYSTEM_VAR_MISSING = "System variable '%s' is missing";
    private static final String ERR_FOLDERS_MISSING = "Folder '%s' is missing";

    public static final String ERR_FILE_MISSING = "File '%s' is missing";


    public static final class Variables {

        private static final String ENVIRONMENT = "env";
        private static final String WORK_DIR = "workDir";
        private static final String JORNEY_DIR = "jorney-files";
        private static final String TESTSDATA_DIR = "test-data";

    }


    private static String environment;
    private static String restEndPoint;
    private static String soapEndPoint;

    private static String workDir;

    private static File requestTemplatesDir;
    private static File responseTemplatesDir;
    private static File jorneyFilesDir;
    private static File dataFilesDir;
    private static File reportTemplatesDir;

    public static String getEnvironment() {
        return environment;
    }

    public static String getRestEndPoint() {
        return restEndPoint;
    }

    public static String getSoapEndPoint() {
        return soapEndPoint;
    }

    public static String getWorkDir() {
        return workDir;
    }

    public static File getRequestTemplatesDir() {
        return requestTemplatesDir;
    }

    public static File getResponseTemplatesDir() {
        return responseTemplatesDir;
    }

    public static File getJourneyFilesDir() {
        return jorneyFilesDir;
    }

    public static File getDataFilesDir() {
        return dataFilesDir;
    }

    public static File getReportTemplatesDir() {
        return reportTemplatesDir;
    }


    /**
     * This method loads the config data needed for application working properly.
     *
     * @throws CriticalException
     */
    public static void init() throws CriticalException {
        workDir = System.getProperty(Variables.WORK_DIR);
        if (StringUtils.isEmpty(workDir)) {
            throw new CriticalException(String.format(ERR_SYSTEM_VAR_MISSING, Variables.WORK_DIR));
        }
        File workDirFile = new File(workDir);
        if (!workDirFile.exists()) {
            throw new CriticalException(String.format(ERR_FOLDERS_MISSING, workDirFile.getAbsolutePath()));
        }

        File configFile = FileUtils.getFile(workDirFile, "config.properties");
        try {
            Map<String, String> configFileProps = PropertyUtils.getProperties(configFile);
            String key = Variables.ENVIRONMENT;
            environment = configFileProps.get(key);
            if (StringUtils.isEmpty(environment)) {
                throw new CriticalException(String.format("Property '%s' is missing from %s", key, configFile.getAbsolutePath()));
            }

            key = String.format("rest.endpoint.%s", environment);
            restEndPoint = configFileProps.get(key);
            if (StringUtils.isEmpty(restEndPoint)) {
                throw new CriticalException(String.format("Property '%s' is missing from %s", key, configFile.getAbsolutePath()));
            }

            key = String.format("soap.endpoint.%s", environment);
            soapEndPoint = configFileProps.get(key);
            if (StringUtils.isEmpty(restEndPoint)) {
                throw new CriticalException(String.format("Property '%s' is missing from %s", key, configFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            throw new CriticalException(e.getMessage());
        }


        requestTemplatesDir = FileUtils.getFile(workDirFile, "templates", "request");
        if (!requestTemplatesDir.exists()) {
            throw new CriticalException(String.format(ERR_FOLDERS_MISSING, requestTemplatesDir.getAbsolutePath()));
        }

        responseTemplatesDir = FileUtils.getFile(workDirFile, "templates", "response");
        if (!responseTemplatesDir.exists()) {
            throw new CriticalException(String.format(ERR_FOLDERS_MISSING, responseTemplatesDir.getAbsolutePath()));
        }

        jorneyFilesDir = FileUtils.getFile(workDirFile, Variables.JORNEY_DIR);
        if (!jorneyFilesDir.exists()) {
            throw new CriticalException(String.format(ERR_FOLDERS_MISSING, Variables.JORNEY_DIR));
        }

        dataFilesDir = FileUtils.getFile(workDirFile, Variables.TESTSDATA_DIR);
        if (!dataFilesDir.exists()) {
            throw new CriticalException(String.format(ERR_FOLDERS_MISSING, Variables.TESTSDATA_DIR));
        }

        URL reportTemplatesDirURL = Config.class.getResource("/report-templates");
        if (reportTemplatesDirURL == null) {
            throw new CriticalException("report templates directory is missing from resources '/report-templates'");
        }
        reportTemplatesDir = new File(reportTemplatesDirURL.getFile());

    }

}
