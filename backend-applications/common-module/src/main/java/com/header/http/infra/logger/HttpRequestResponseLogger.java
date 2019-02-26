package com.header.http.infra.logger;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestResponseLogger {

    private HttpRequestResponseFormatter formatter;

    private HttpRequestResponseLogger() {}

    public HttpRequestResponseLogger of(HttpRequestResponseFormatter formatter) {
        HttpRequestResponseLogger logger = new HttpRequestResponseLogger();
        logger.formatter = formatter;
        return logger;
    }

    public void logRequest(HttpRequestInfo requestInfo) {
        String info = formatter.getRequestString(requestInfo);
        log.info(info);
    }

    public void logResponse(HttpRequestInfo requestInfo, HttpResponseInfo responseInfo, long duration) {
        String info = formatter.getResponseString(requestInfo, responseInfo, duration);
        log.info(info);
    }

}
