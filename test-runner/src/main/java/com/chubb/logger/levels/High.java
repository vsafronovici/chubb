package com.chubb.logger.levels;

import org.apache.log4j.Level;

/**
 * Created by ichistruga on 11/1/2016.
 */
public class High extends Level {

    static final int HIGH_IDX = 23000;

    public static final Level HIGH = new High(HIGH_IDX, "HIGH", 6);

    protected High(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);

    }


    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("HIGH")) {
            return HIGH;
        }
        return (Level) toLevel(logArgument, Level.DEBUG);
    }

    public static Level toLevel(int val) {
        if (val == HIGH_IDX) {
            return HIGH;
        }
        return (Level) toLevel(val, Level.DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        if (val == HIGH_IDX) {
            return HIGH;
        }
        return Level.toLevel(val, defaultLevel);
    }

    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("HIGH")) {
            return HIGH;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }
}
