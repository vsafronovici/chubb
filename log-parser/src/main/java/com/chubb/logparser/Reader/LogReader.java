package com.chubb.logparser.Reader;

import com.chubb.logparser.Utils.RegExPatterns;
import com.chubb.logparser.Entry.ReportEntry;
import com.chubb.logparser.Utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class LogReader {

//    private long id = 0;

    public static ReportEntry readReportData(String filepath) throws RuntimeException, IOException, ParseException {
        FileReader fileReader = new FileReader(new File(filepath));
        BufferedReader br = new BufferedReader(fileReader);
        ReportEntry parentEntry = new ReportEntry();
        return new LogReader().readLines(br, parentEntry);
    }

    private ReportEntry readLines(BufferedReader reader, ReportEntry parent) throws RuntimeException, IOException, ParseException {

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty() && !canHandle(line)) continue;

            parent.getChildren().add(processLine(line));

        }
        return parent;
    }

    private ReportEntry processLine(String startLine) throws RuntimeException, ParseException {
        Matcher matcher = RegExPatterns.match(Constants.LOGENTRY_PATTERN, startLine);
        if (matcher != null) {
            ReportEntry entry = new ReportEntry();
            entry.setLevel(matcher.group(Constants.LEVEL));
            entry.setThreadName(matcher.group(Constants.THREADNAME));
            entry.setStepName(matcher.group(Constants.STEP));

            String messageString =matcher.group(Constants.MESSAGE);
            // Execution Time
            Matcher totalExecutionTimeMatcher = RegExPatterns.match(Constants.EXECUTIONTIME_PATTERN, messageString);
            if (totalExecutionTimeMatcher.groupCount() > 0) {
                String foundExpression = totalExecutionTimeMatcher.group(0);
                Matcher executionTimeMatcher = RegExPatterns.match(Constants.INTNUMBER_PATTERN, foundExpression);
                if (executionTimeMatcher.groupCount() > 0) {
                    String foundExecutionTime = executionTimeMatcher.group(0);
                    if (foundExecutionTime.chars().allMatch(Character::isDigit)) {
                        entry.setExecutionTime(Long.parseLong(foundExecutionTime));
                        messageString = messageString.replace(foundExpression, "");
                    }
                }
            }

            // Parameters
            Matcher parametersMatcher = RegExPatterns.match(Constants.PARAMETERS_PATTERN, messageString);
            if (parametersMatcher.groupCount() > 0) {
                entry.setParametersList(parametersMatcher.group(0));
                messageString = messageString.replace(entry.getParametersList(), "");
            }
            entry.setMessage(messageString.trim());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entry.setEntryDate(formatter.parse(matcher.group(Constants.DATETIMESTAMP)));
            return entry;
        }
        throw new RuntimeException("Line [" + startLine + "] doesn't match given pattern [" + Constants.LOGENTRY_PATTERN + "]");
    }

    private boolean canHandle(String line) {
        return RegExPatterns.match(Constants.LOGENTRY_PATTERN, line) != null;
    }

}
