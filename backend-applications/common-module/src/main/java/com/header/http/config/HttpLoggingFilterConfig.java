package com.header.http.config;

import com.header.http.infra.filter.HttpLoggingFilter;
import com.header.http.infra.logger.HttpRequestResponseFormatter;
import com.header.http.infra.logger.HttpRequestResponseLogger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;


@Configuration
public class HttpLoggingFilterConfig {

    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean loggingFilterRegistration = new FilterRegistrationBean();
        loggingFilterRegistration.setFilter(this.httpLoggingFilter());
        loggingFilterRegistration.setName("httpLoggingFilter");
        return loggingFilterRegistration;
    }

    @Bean(name = {"httpLoggingFilter"})
    public Filter httpLoggingFilter() {
        return new HttpLoggingFilter(HttpRequestResponseLogger.of(new HttpRequestResponseFormatter()));
    }
}
