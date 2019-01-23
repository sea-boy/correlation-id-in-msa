package com.header.correlation.infra.filter.logger;

import com.header.correlation.infra.filter.core.ContextField;
import com.header.correlation.infra.filter.core.CorrelationContext;
import com.header.correlation.infra.filter.header.CorrelationHeaderUtil;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public class CorrelationLoggerUtil {

    private static final Map<CorrelationLoggerField, ContextField> CONTEXT_MAP = new HashMap<>();

    static {
        CONTEXT_MAP.put(CorrelationLoggerField.CORRELATION_ID, ContextField.CORRELATION_ID);
        CONTEXT_MAP.put(CorrelationLoggerField.USER_ID, ContextField.USER_ID);
    }

    public static void updateCorrelation() {
        for (CorrelationLoggerField field : CorrelationLoggerField.values()) {
            ContextField contextField = CONTEXT_MAP.get(field);
            MDC.put(field.getValue(), CorrelationContext.get(contextField));
        }
    }

    public static void clear() {
        MDC.clear();
    }
}
