package com.header.correlation.infra.filter.header;

public enum CorrelationHeaderField {
    CORRELATION_ID("header-correlation-id"),
    USER_ID("header-user-id");

    private String value;

    CorrelationHeaderField(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
