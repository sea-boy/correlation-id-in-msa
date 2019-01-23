package com.header.correlation.infra.filter.header;

import com.header.correlation.infra.filter.core.CorrelationContext;
import com.header.correlation.infra.filter.header.CorrelationHeaderField;
import com.header.correlation.infra.filter.header.CorrelationHeaderUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CorrelationHttpHeaderHelper {

    public static void prepareCorrelationParams(HttpServletRequest httpServletRequest) {
        String currentCorrelationId = prepareCorrelationId(httpServletRequest);
        setCorrelations(httpServletRequest, currentCorrelationId);
        log.debug("Request Correlation Parameters : ");
        CorrelationHeaderField[] headerFields = CorrelationHeaderField.values();
        for (CorrelationHeaderField field : headerFields) {
            String value = CorrelationHeaderUtil.get(field);
            log.debug("{} : {}", field.getValue(), value);
        }
    }

    private static String prepareCorrelationId(HttpServletRequest httpServletRequest) {
        String currentCorrelationId = httpServletRequest.getHeader(CorrelationHeaderField.CORRELATION_ID.getValue());
        if (currentCorrelationId == null) {
            currentCorrelationId = CorrelationContext.generateId();
            log.trace("Generated Correlation Id: {}", currentCorrelationId);
        } else {
            log.trace("Incoming Correlation Id: {}", currentCorrelationId);
        }
        return currentCorrelationId;
    }

    private static void setCorrelations(HttpServletRequest httpServletRequest, String correlationId) {
        CorrelationHeaderUtil.set(CorrelationHeaderField.CORRELATION_ID, correlationId);
        CorrelationHeaderField[] headerFields = CorrelationHeaderField.values();
        for (CorrelationHeaderField field : headerFields) {
            if (CorrelationHeaderField.CORRELATION_ID != field) {
                CorrelationHeaderUtil.set(field, httpServletRequest.getHeader(field.getValue()));
            }
        }
    }

}
