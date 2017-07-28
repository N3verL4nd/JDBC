package com.xiya.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by N3verL4nd on 2017/4/24.
 */
public class LogDemo {
    private static Log log = LogFactory.getLog(LogDemo.class);

    public static void main(String[] args) {
        log.debug("This is debug message.");
        log.info("This is info message.");
        log.warn("This is warn message.");
        log.error("This is error message.");
    }
}