package com.header.correlation.config;

import com.header.correlation.infra.filter.CorrelationIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class CorrelationFilterConfig {

    @Bean
    public FilterRegistrationBean correlationIdFilterRegistration() {
        FilterRegistrationBean correlationIdFilterRegistration = new FilterRegistrationBean();
        correlationIdFilterRegistration.setFilter(this.correlationIdFilter());
        correlationIdFilterRegistration.setName("correlationIdFilter");
        correlationIdFilterRegistration.setOrder(1);
        return correlationIdFilterRegistration;
    }

    @Bean(name = {"correlationIdFilter"})
    public Filter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

}
