package com.header.correlation.infra.message;

import com.header.correlation.biz.OtherStringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Slf4j
@Component
public class OtherStringGenImpl implements OtherStringGenerator {

    private RestTemplate restTemplate;

    private static final String URL_AS_CHAR = "http://localhost:8001/as/api/char";
    private static final String URL_BS_CHAR = "http://localhost:8002/bs/api/char";

    @Autowired
    public OtherStringGenImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getCharA() {
        URI uri = URI.create(URL_AS_CHAR);
        ResponseEntity<CharRes> res = null;
        try {
            res = restTemplate.getForEntity(uri, CharRes.class);
        } catch (Exception ex) {
            log.info("error: {}", ex);
        }
        return Optional.ofNullable(res)
                .map(s -> s.getBody().getCharInfo())
                .orElse(null);
    }

    @Override
    public String getCharB() {
        URI uri = URI.create(URL_BS_CHAR);
        ResponseEntity<CharRes> res = null;
        try {
            res = restTemplate.getForEntity(uri, CharRes.class);
        } catch (Exception ex) {
            log.info("error: {}", ex);
        }
        return Optional.ofNullable(res)
                .map(s -> s.getBody().getCharInfo())
                .orElse(null);
    }
}
