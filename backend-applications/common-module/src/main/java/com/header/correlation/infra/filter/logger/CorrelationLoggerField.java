package com.header.correlation.infra.filter.logger;

public enum CorrelationLoggerField {
    CORRELATION_ID("log-correlation-id"),
    USER_ID("log-user-id");

    private String value;

    CorrelationLoggerField(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
