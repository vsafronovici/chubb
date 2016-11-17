package com.chubb.util;

import com.chubb.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by vsafronovici on 10/11/2016.
 */

@Service
public class TemplateInjector {

    @Autowired
    private Storage storage;

    public String inject(String inputString) {
        String data = injectCsvData(inputString);
        return injectStorageData(data);
    }

    public String injectCsvData(String inputString) {
        String data = inputString;
        String template = "##";
        for (Map.Entry<String, String> pair : storage.getCsvData().entrySet()) {
            String seeker = String.format("%s%s%s", template,  pair.getKey(), template);
            data = data.replaceFirst(seeker, pair.getValue());
        }

        return data;
    }

    public String injectStorageData(String inputString) {
        String data = inputString;
        String template = "%%";
        for (Map.Entry<String, Object> pair : storage.getContextData().entrySet()) {
            if ( pair.getValue()  instanceof String) {
                String replaceValue =  (String) pair.getValue();
                String templateKeyWord = String.format("%s%s%s", template, pair.getKey(), template);
                data = data.replaceFirst(templateKeyWord, replaceValue);
            }
        }

        return data;
    }
}
