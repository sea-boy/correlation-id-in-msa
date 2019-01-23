package com.header.correlation.infra.filter.core;

import com.header.correlation.infra.filter.core.ContextField;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CorrelationContext {

    private static final String ID_HEADER_FORMAT = "yyMMddHHmmss";
    private static ThreadLocal<Map<ContextField, String>> contextMap = ThreadLocal.withInitial(() -> new HashMap<>());

    public static String generateId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ID_HEADER_FORMAT);
        String date = simpleDateFormat.format(new Date());
        return date + "-" + getRandomHexString(7);
    }

    private static String getRandomHexString(int num) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        while(stringBuilder.length() < num) {
            stringBuilder.append(Integer.toHexString(random.nextInt()));
        }

        return stringBuilder.toString().substring(0, num);
    }

    public static String get(ContextField field) {
        return contextMap.get().get(field);
    }

    public static void set(ContextField field, String value) {
        contextMap.get().put(field, value);
    }
}
