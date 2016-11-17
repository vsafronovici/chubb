package com.chubb.logger.levels;

import org.apache.log4j.Level;

/**
 * Created by ichistruga on 11/1/2016.
 */
public class Normal extends Level {

    static final int NORMAL_IDX = 22000;

    public static final Level NORMAL = new Normal(NORMAL_IDX, "NORMAL", 6);

    protected Normal(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);

    }


    public static Level toLevel(String logArgument) {
        if (logArgument != null && logArgument.toUpperCase().equals("NORMAL")) {
            return NORMAL;
        }
        return (Level) toLevel(logArgument, Level.DEBUG);
    }

    public static Level toLevel(int val) {
        if (val == NORMAL_IDX) {
            return NORMAL;
        }
        return (Level) toLevel(val, Level.DEBUG);
    }

    public static Level toLevel(int val, Level defaultLevel) {
        if (val == NORMAL_IDX) {
            return NORMAL;
        }
        return Level.toLevel(val, defaultLevel);
    }

    public static Level toLevel(String logArgument, Level defaultLevel) {
        if (logArgument != null && logArgument.toUpperCase().equals("NORMAL")) {
            return NORMAL;
        }
        return Level.toLevel(logArgument, defaultLevel);
    }
}
