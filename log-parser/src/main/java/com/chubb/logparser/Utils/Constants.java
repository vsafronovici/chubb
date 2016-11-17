package com.chubb.logparser.Utils;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class Constants {

    public static final String LOGENTRY_PATTERN =
            RegExPatterns.TEXT
                    + RegExPatterns.SPACE
                    + RegExPatterns.DATETIME_SIMPLE
                    + RegExPatterns.SPACE
                    + RegExPatterns.WORD
                    + RegExPatterns.SPACE
                    + RegExPatterns.WORD
                    + RegExPatterns.SPACE
                    + RegExPatterns.ANY_TEXT
                    + RegExPatterns.LINE_END;


    public static final String EXECUTIONTIME_PATTERN =
            "([(]?\\bexecuted\\b\\s\\bin\\b\\s-?[0-9]+(?:,[0-9]+)?\\s\\bmillis\\b[)]?)";

    public static final String PARAMETERS_PATTERN =
            "(\\(\\bparamName\\b.*\\))";


    public static final String INTNUMBER_PATTERN =
            "(" + RegExPatterns.INT + ")";

    public static final String LOGENTRY_ERROR_PATTERN =
            RegExPatterns.TEXT
                    + RegExPatterns.SPACE
                    + RegExPatterns.DATETIME_SIMPLE
                    + RegExPatterns.SPACE
                    + "(ERROR)"
                    + RegExPatterns.SPACE
                    + RegExPatterns.WORD
                    + RegExPatterns.SPACE
                    + RegExPatterns.ANY_TEXT
                    + RegExPatterns.LINE_END;


    public static final int THREADNAME = 1;
    public static final int DATETIMESTAMP = 2;
    public static final int LEVEL = 3;
    public static final int STEP = 4;
    public static final int MESSAGE = 5;

}
