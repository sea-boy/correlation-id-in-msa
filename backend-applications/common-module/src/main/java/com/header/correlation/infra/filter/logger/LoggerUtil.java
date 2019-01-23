package com.header.correlation.infra.filter.logger;


import org.slf4j.MDC;

public class LoggerUtil {


    public static void updateCorrelation() {
        MDC.put("", "");
    }

    public static void clear() {
        MDC.clear();
    }
}
