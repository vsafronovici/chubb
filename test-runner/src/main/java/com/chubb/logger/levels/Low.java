package com.chubb.logger.levels;

import org.apache.log4j.Level;

/**
 * Created by ichistruga on 11/1/2016.
 */
public class Low extends Level {

    static final int LOW_IDX = 21000;

    public static final Level LOW = new Low(LOW_IDX, "LOW", 6);

    protected Low(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);

    }


    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("LOW")) {
            return LOW;
        }
        return (Level) toLevel(logArgument, Level.DEBUG);
    }

    public static Level toLevel(int val) {
        if (val == LOW_IDX) {
            return LOW;
        }
        return (Level) toLevel(val, Level.DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        if (val == LOW_IDX) {
            return LOW;
        }
        return Level.toLevel(val, defaultLevel);
    }

    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("LOW")) {
            return LOW;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }
}
