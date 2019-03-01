package com.header.http.infra.intercepter;

import com.header.http.infra.logger.HttpRequestInfo;
import com.header.http.infra.logger.HttpRequestResponseLogger;
import com.header.http.infra.logger.HttpResponseInfo;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.Charset;


public class LoggingRequestIntercepter implements ClientHttpRequestInterceptor {

    private HttpRequestResponseLogger logger;

    public LoggingRequestIntercepter(HttpRequestResponseLogger logger) {
        this.logger = logger;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpRequestInfo requestInfo = HttpRequestInfo.builder()
                .httpMethod(request.getMethod().name())
                .uri(request.getURI().toString())
                .body(new String(body))
                .build();
        logger.logRequest(requestInfo);

        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        long duration = System.currentTimeMillis() - start;

        HttpResponseInfo responseInfo = HttpResponseInfo.builder()
                .httpStatus(response.getStatusCode().toString())
                .body(IOUtils.toString(response.getBody(), Charset.defaultCharset()))
                .build();
        logger.logResponse(requestInfo, responseInfo, duration);
        return response;
    }
}
