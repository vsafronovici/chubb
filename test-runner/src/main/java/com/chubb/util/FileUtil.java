package com.chubb.util;

import com.chubb.app.Config;
import com.chubb.exception.SevereException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by vsafronovici on 10/12/2016.
 */
public final class FileUtil {

    public static String readFileToString(File file) {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new SevereException(e.getMessage());
        }
    }

    public static File getFileFromDir(File dir, String fileName) {
        File file = FileUtils.getFile(dir, fileName);
        if (!file.exists()) {
            throw new SevereException(String.format("File '%s' is missing", file.getAbsolutePath()));
        }
        return file;
    }

    public static File getRequestTemplateFile(String fileName) {
        return getFileFromDir(Config.getRequestTemplatesDir(), fileName);
    }

    public static File getTestDataFile(String fileName) {
        return getFileFromDir(Config.getDataFilesDir(), fileName);
    }

    public static File getResponseTemplateFile(String fileName) {
        return getFileFromDir(Config.getResponseTemplatesDir(), fileName);
    }



}
