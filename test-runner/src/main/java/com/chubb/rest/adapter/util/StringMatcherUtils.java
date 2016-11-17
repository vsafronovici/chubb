package com.chubb.rest.adapter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ichistruga on 10/24/2016.
 */
public class StringMatcherUtils {

    private static final String fileRegEx = "\\w+[.]\\w+";
    private static final String indexRegEx = "\\Q[\\E\\d+\\Q]\\E";
    private static final String indexBoundsRegEx = "[\\Q[\\E\\Q]\\E]";
    private static final String parameterValueShoulBeInjectedRegEx = "\\Q%%\\E\\w+\\Q%%\\E";
    private static final String integerRegEx = "^-?\\d+$";

    private static String matchExpresion(String stringValue, String regexValue) {
        Pattern pattern = Pattern.compile(regexValue);
        Matcher matcher = pattern.matcher(stringValue);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        if (result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public static String replaceExpresion(String stringValue, String regExValue, String replaceValue) {
        Pattern pattern = Pattern.compile(regExValue);
        Matcher matcher = pattern.matcher(stringValue);

        return matcher.replaceAll(replaceValue);
    }

    public static boolean matchValueInjection(String stringValue) {
        Pattern pattern = Pattern.compile(parameterValueShoulBeInjectedRegEx);
        Matcher matcher = pattern.matcher(stringValue);

        return matcher.matches();
    }

    public static boolean matchIntegerValue(String stringValue) {
        Pattern pattern = Pattern.compile(integerRegEx);
        Matcher matcher = pattern.matcher(stringValue);

        return matcher.matches();
    }

    public static String replaceFirstExpresion(String stringValue, String regExValue, String replaceValue) {
        Pattern pattern = Pattern.compile(regExValue);
        Matcher matcher = pattern.matcher(stringValue);

        return matcher.replaceFirst(replaceValue);
    }

    public static String matchFileName(String stringValue) {
        return matchExpresion(stringValue, fileRegEx);
    }

    public static String matchIndex(String stringValue) {
        return matchExpresion(stringValue, indexRegEx);
    }

    public static String replaceIndexBoundaries(String stringValue) {
        return replaceExpresion(stringValue, indexBoundsRegEx, "");
    }

}