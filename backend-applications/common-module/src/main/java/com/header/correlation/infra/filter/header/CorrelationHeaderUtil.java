package com.header.correlation.infra.filter.header;

import com.header.correlation.infra.filter.core.ContextField;
import com.header.correlation.infra.filter.core.CorrelationContext;
import com.header.correlation.infra.filter.header.CorrelationHeaderField;

import java.util.HashMap;
import java.util.Map;

public class CorrelationHeaderUtil {

    private static final Map<CorrelationHeaderField, ContextField> CONTEXT_MAP = new HashMap<>();

    static {
        CONTEXT_MAP.put(CorrelationHeaderField.CORRELATION_ID, ContextField.CORRELATION_ID);
        CONTEXT_MAP.put(CorrelationHeaderField.USER_ID, ContextField.USER_ID);
    }

    public static String get(CorrelationHeaderField headerField) {
        ContextField contextField = CONTEXT_MAP.get(headerField);
        return CorrelationContext.get(contextField);
    }

    public static void set(CorrelationHeaderField headerField, String value) {
        ContextField contextField = CONTEXT_MAP.get(headerField);
        CorrelationContext.set(contextField, value);
    }

}
