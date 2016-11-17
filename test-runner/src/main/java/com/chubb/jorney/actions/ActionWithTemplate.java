package com.chubb.jorney.actions;

import com.chubb.exception.SevereException;
import com.chubb.rest.adapter.util.StringMatcherUtils;
import com.chubb.util.CsvReader;
import com.chubb.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by vsafronovici on 11/2/2016.
 */
public abstract class ActionWithTemplate extends Action {

    protected abstract File getTemplateDir();


    @Override
    public void execute(String paramName, String paramValue) {
        super.execute(paramName, paramValue);

        getTemplateFile(paramName); //check if file exists
        clearCsvData();
        loadCsvData(paramValue);

    }

    @Override
    public void assertExecutionArguments(String paramName, String paramValue) {
        super.assertExecutionArguments(paramName, paramValue);

        if ((StringMatcherUtils.matchFileName(paramValue) == null) || (StringMatcherUtils.matchIndex(paramValue) == null)) {
            throw new SevereException(String.format("Provided invalid test data value: '%s'", paramValue));
        }

    }

    protected File getTemplateFile(String paramName) {
        return FileUtil.getFileFromDir(getTemplateDir(), paramName);
    }

    private void clearCsvData() {
        getCsvData().clear();
    }

    private void loadCsvData(String paramValue) {

        String testDataFileName = StringMatcherUtils.matchFileName(paramValue);
        File testDataFile = FileUtil.getTestDataFile(testDataFileName);

        String testDataIndex = StringMatcherUtils.replaceIndexBoundaries(StringMatcherUtils.matchIndex(paramValue)).trim();

        if (testDataIndex.isEmpty()) {
            throw new SevereException(String.format("Provided invalid test data index '%s'", paramValue));
        }


        try {
            List<Map<String, String>> testDataCsv = CsvReader.readCSV(testDataFile);
            int index = Integer.parseInt(testDataIndex);
            if (index >= testDataCsv.size()) {
                throw new SevereException(String.format("Invalid line '%d' for %s", index, testDataFile.getAbsolutePath()));
            }
            Map<String, String> testData = testDataCsv.get(index);
            testData.forEach((s, s2) -> getCsvData().put(s, s2));

        } catch (IOException e) {
            throw new SevereException(e.getMessage(), e);
        }
    }


}
