package com.header.correlation.infra.message;

import com.header.correlation.biz.OtherStringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class OtherStringGenImpl implements OtherStringGenerator {

    private RestTemplate restTemplate;

    @Autowired
    public OtherStringGenImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getCharB() {
        URI uri = URI.create("http://localhost:8002/bs/api/char");
        CharRes charRes = null;
        try {
            charRes = restTemplate.getForObject(uri, CharRes.class);
        } catch (Exception ex) {
            log.error("error: {}", ex);
        }
        return charRes.getCharInfo();
    }

    @Override
    public String getCharC() {
        URI uri = URI.create("http://localhost:8003/cs/api/char");
        CharRes charRes = null;
        try {
            charRes = restTemplate.getForObject(uri, CharRes.class);
        } catch (Exception ex) {
            log.error("error: {}", ex);
        }
        return charRes.getCharInfo();
    }
}
