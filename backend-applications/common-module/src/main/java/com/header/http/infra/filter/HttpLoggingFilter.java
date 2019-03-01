package com.header.http.infra.filter;


import com.header.http.infra.logger.HttpRequestInfo;
import com.header.http.infra.logger.HttpRequestResponseLogger;
import com.header.http.infra.logger.HttpResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class HttpLoggingFilter extends OncePerRequestFilter {

    private HttpRequestResponseLogger logger;

    public HttpLoggingFilter(HttpRequestResponseLogger logger) {
        this.logger = logger;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CachedRequestWrapper requestWrapper = new CachedRequestWrapper(request);
        HttpRequestInfo requestInfo = HttpRequestInfo.builder()
                .httpMethod(requestWrapper.getMethod())
                .uri(requestWrapper.getRequestURI())
                .body(IOUtils.toString(requestWrapper.getInputStream(), Charset.defaultCharset()))
                .build();
        logger.logRequest(requestInfo);

        CachedResponseWrapper responseWrapper = new CachedResponseWrapper(response);
        long startTime = System.currentTimeMillis();

        filterChain.doFilter(requestWrapper, responseWrapper);

        long duration = System.currentTimeMillis() - startTime;

        HttpResponseInfo responseInfo = HttpResponseInfo.builder()
                .body(responseWrapper.getResponseBodyAsString())
                .build();
        logger.logResponse(requestInfo, responseInfo, duration);
    }
}
