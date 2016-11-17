package com.chubb.logparser.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class RegExPatterns {

    public static final String SPACES = "\\s*";
    public static final String SPACE = "\\s";

    public static final String TEXT = "([^\"]*)";
    public static final String WORD = "(\\w*)";
    public static final String ANY_TEXT = "((.+))";
    public static final String LINE_START = "^";
    public static final String LINE_END = "$";

    public static final String INT = "-?[0-9]+(?:,[0-9]+)?";

    public static final String DATETIME =
            "((19|20\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) ([01]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))";
    public static final String DATETIME_SIMPLE =
            "(\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d)";

    public static Matcher match(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher : null;
    }
}
