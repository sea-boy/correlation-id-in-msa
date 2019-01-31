package com.header.correlation.infra.message;

import com.header.correlation.biz.OtherStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OtherStringGenImpl implements OtherStringGenerator {

    private RestTemplate restTemplate;

    @Autowired
    public OtherStringGenImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getCharA() {

        return null;
    }

    @Override
    public String getCharC() {
        return null;
    }
}
