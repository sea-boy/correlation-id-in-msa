package com.header.http.infra.logger;




public class HttpRequestResponseFormatter {

    private static final String REQUEST = "[REQ]";
    private static final String RESPONSE = "[RES]";
    private static final String TIME_UNIT = "ms";

    public String getRequestString(HttpRequestInfo requestInfo) {
        String info = new StringBuilder(REQUEST)
                .append(requestInfo.getHttpMethod().name())
                .append(" ")
                .append(requestInfo.getUri())
                .append(" ")
                .append(new String(requestInfo.getBody()))
                .toString();
        return info;
    }

    public String getResponseString(HttpRequestInfo requestInfo, HttpResponseInfo responseInfo, long duration) {
        String info = new StringBuilder(RESPONSE)
                .append(requestInfo.getHttpMethod().name())
                .append(" ")
                .append(requestInfo.getUri())
                .append(" ")
                .append(responseInfo.getHttpStatus())
                .append(responseInfo.getBody())
                .append(" ")
                .append(duration)
                .append(" ")
                .append(TIME_UNIT)
                .toString();
        return info;
    }

}
