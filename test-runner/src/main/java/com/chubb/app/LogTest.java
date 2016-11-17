package com.chubb.app;

import org.apache.log4j.Logger;

/**
 * Created by vsafronovici on 10/27/2016.
 */
@Deprecated
public class LogTest {

    static Logger log = Logger.getLogger(LogTest.class.getName());


    public static void main(String[] args) {

        new Thread("myThread") {
            @Override
            public void run() {
                log.debug("hello");
            }
        }.start();

    }


}
