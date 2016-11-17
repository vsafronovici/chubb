package com.chubb.logparser;

import com.chubb.logparser.Utils.Constants;
import com.chubb.logparser.Utils.RegExPatterns;

import java.util.regex.Matcher;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class App {

    public static void main(String[] args) {
        String s = "main 2016-11-07 18:28:01 NORMAL ValidateResponseHttpStatusCode executed in 3 millis";

        String pattern = Constants.LOGENTRY_PATTERN;

        Matcher matcher = RegExPatterns.match(pattern, s);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));

        System.out.println("----------------------------------------");

        s = "main 2016-11-07 18:28:01 ERROR ValidateResponseHttpStatusCode executed in 3 millis";

        pattern = RegExPatterns.TEXT
                + RegExPatterns.SPACE
                + RegExPatterns.DATETIME_SIMPLE
                + RegExPatterns.SPACE
                + "(ERROR)"
                + RegExPatterns.SPACE
                + RegExPatterns.WORD
                + RegExPatterns.SPACE
                + RegExPatterns.ANY_TEXT
                + RegExPatterns.LINE_END;

        matcher = RegExPatterns.match(pattern, s);

        for (int i = 0; i < matcher.groupCount(); i++)
            System.out.println(matcher.group(i));
    }

}
