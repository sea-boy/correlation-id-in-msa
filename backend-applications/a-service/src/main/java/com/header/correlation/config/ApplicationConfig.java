package com.header.correlation.config;


import com.header.http.config.EnableHttpLogging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@EnableCorrelationId
@EnableHttpLogging
@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
