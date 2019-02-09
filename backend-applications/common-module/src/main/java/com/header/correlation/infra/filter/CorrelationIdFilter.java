package com.header.correlation.infra.filter;


import com.header.correlation.infra.filter.header.CorrelationHttpHeaderHelper;
import com.header.correlation.infra.filter.logger.CorrelationLoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class CorrelationIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CorrelationHttpHeaderHelper.prepareCorrelationParams(request);
        CorrelationLoggerUtil.updateCorrelation();
        filterChain.doFilter(request, response);
        CorrelationLoggerUtil.clear();
    }
 }
