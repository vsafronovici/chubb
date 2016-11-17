package com.chubb;

import com.chubb.logparser.Utils.Constants;
import com.chubb.logparser.Utils.RegExPatterns;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class RegExPatternsTest {

    private static final String errorLogEntryRecord =
            "main 2016-11-07 18:28:01 ERROR ValidateResponseHttpStatusCode executed in 3 millis";
    private static final String errorLogEntryRecord2 =
            "testRestResponseValidatorFailsByTemplate2.csv 2016-11-11 11:59:22 ERROR ValidateRestResponseByTemplate failed (executed in 24 millis)(paramName=resp_tmpl_update_user_body_fails.json, paramValue=RespEditUserData.csv[0]) Reason: Response body contains field 'address' that is not expected";
    private static final String normalLogEntryRecord =
            "testGenericHttpResponseValidator.csv 2016-11-11 11:59:22 NORMAL SendRestRequest (executed in 250 millis)(paramName=req_tmpl_update_user.json, paramValue=ReqEditUserData.csv[0])";
    private static final String parameterLogEntry = "(paramName=address.zip, paramValue=%%zip%%)";
    private static final String normalLogEntryRecord_InjectedValue =
            "testRestResponseValidatorFailsByPath.csv 2016-11-11 17:48:59 NORMAL ValidateRestResponseByPath (executed in 0 millis)(paramName=address.zip, paramValue=%%zip%%)";
    private static final String errorLogEntryRecord_InjectedValue =
            "testRestResponseValidatorFailsByPath.csv 2016-11-11 17:48:59 ERROR ValidateRestResponseByPath failed (executed in 143 millis)(paramName=address.zip, paramValue=%%zip%%) Reason: Response body contains field 'address' that is not expected";

    @Test
    public void TestPatterns_AnyStringValue() {
        String s = "DEBUG";

        Assert.assertNotNull(RegExPatterns.match(RegExPatterns.ANY_TEXT, s));

    }

    @Test
    public void TestPatterns_DateTimeValue() {
        String s = "2016-11-07 10:30:34";

        Assert.assertNotNull(RegExPatterns.match(RegExPatterns.DATETIME, s));

    }

    @Test
    public void TestPatterns_StringExpression() {
        String s = "main DEBUG";

        String pattern = RegExPatterns.ANY_TEXT + RegExPatterns.SPACE + RegExPatterns.ANY_TEXT;

        Assert.assertNotNull(RegExPatterns.match(pattern, s));

    }

    @Test
    public void TestPatterns_LogEntryExpression() {
        Assert.assertNotNull(RegExPatterns.match(Constants.LOGENTRY_PATTERN, normalLogEntryRecord));

    }

    @Test
    public void TestPatterns_LogEntryNoErrorExpression() {
        Assert.assertNull(RegExPatterns.match(Constants.LOGENTRY_ERROR_PATTERN, normalLogEntryRecord));

    }

    @Test
    public void TestPatterns_LogEntryErrorExpression() {
        Assert.assertNotNull(RegExPatterns.match(Constants.LOGENTRY_ERROR_PATTERN, errorLogEntryRecord));
    }

    @Test
    public void TestPatterns_LogEntryParsing() {
        Matcher matcher = RegExPatterns.match(Constants.LOGENTRY_ERROR_PATTERN, errorLogEntryRecord);
        Assert.assertTrue(matcher.group(3).equalsIgnoreCase("ERROR"));

    }

    @Test
    public void TestPatterns_LogEntryParsingAndDisplaying() {

        Matcher matcher = RegExPatterns.match(Constants.LOGENTRY_ERROR_PATTERN, errorLogEntryRecord);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.group(3).equalsIgnoreCase("ERROR"));

    }

    @Test
    public void TestPatterns_LogEntryParsingAndDisplayingError() {

        Matcher matcher = RegExPatterns.match(Constants.LOGENTRY_ERROR_PATTERN, errorLogEntryRecord2);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.group(3).equalsIgnoreCase("ERROR"));

    }

    @Test
    public void TestPatterns_LogEntryParsingAndDisplayingNormal() {

        Matcher matcher = RegExPatterns.match(Constants.LOGENTRY_PATTERN, normalLogEntryRecord);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.group(3).equalsIgnoreCase("NORMAL"));

    }

    @Test
    public void TestPatterns_ExecutionTimeMatching_ErrorLog() {

        Matcher matcher = RegExPatterns.match(Constants.EXECUTIONTIME_PATTERN, errorLogEntryRecord2);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);

        String foundExpression = matcher.group(0);
        Matcher matcher2 = RegExPatterns.match(Constants.INTNUMBER_PATTERN, foundExpression);

        for (int i = 0; i < matcher2.groupCount(); i++)
            System.out.println(matcher2.group(i));

        Assert.assertTrue(matcher2.group(0).chars().allMatch(Character::isDigit));

    }

    @Test
    public void TestPatterns_ExecutionTimeMatching_NormalLog() {

        Matcher matcher = RegExPatterns.match(Constants.EXECUTIONTIME_PATTERN, normalLogEntryRecord);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);

        String foundExpression = matcher.group(0);
        Matcher matcher2 = RegExPatterns.match(Constants.INTNUMBER_PATTERN, foundExpression);

        for (int i = 0; i < matcher2.groupCount(); i++)
            System.out.println(matcher2.group(i));

        Assert.assertTrue(matcher2.group(0).chars().allMatch(Character::isDigit));

    }

    @Test
    public void TestPatterns_ExecutionParametersMatching_ErrorLog() {

        Matcher matcher = RegExPatterns.match(Constants.PARAMETERS_PATTERN, errorLogEntryRecord2);

        for (int i = 0;i<matcher.groupCount();i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);

    }

    @Test
    public void TestPatterns_ExecutionParametersMatching_NormalLog() {

        Matcher matcher = RegExPatterns.match(Constants.PARAMETERS_PATTERN, normalLogEntryRecord);

        for (int i = 0;i<matcher.groupCount();i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);
    }

    @Test
    public void TestPatterns_ExecutionParametersMatching_NormalLog_InjectedValue() {

        Matcher matcher = RegExPatterns.match(Constants.PARAMETERS_PATTERN, parameterLogEntry);

        for (int i = 0;i<matcher.groupCount();i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);
    }

    @Test
    public void TestPatterns_ExecutionParametersMatching_FullNormalLog_InjectedValue() {

        String regExPattern = "(\\(\\bparamName\\b[=].*[,]\\s\\bparamValue\\b[=].*\\))";
        Matcher matcher = RegExPatterns.match(regExPattern, normalLogEntryRecord_InjectedValue);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);
    }

    @Test
    public void TestPatterns_ExecutionParametersMatching_FullErrorLog_InjectedValue() {

        String regExPattern = "(\\(\\bparamName\\b[=].*[,]\\s\\bparamValue\\b[=].*\\))";
        Matcher matcher = RegExPatterns.match(regExPattern, errorLogEntryRecord_InjectedValue);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        Assert.assertTrue(matcher.groupCount() > 0);
    }
}
