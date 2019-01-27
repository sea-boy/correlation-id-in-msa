package com.header.correlation.infra.message;

import com.header.correlation.biz.LengthGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LengthGeneratorImpl implements LengthGenerator {

    private RestTemplate restTemplate;

    @Autowired
    public LengthGeneratorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public int getLength() {
        return 0;
    }
}
