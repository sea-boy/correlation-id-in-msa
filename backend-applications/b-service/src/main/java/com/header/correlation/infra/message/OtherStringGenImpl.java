package com.header.correlation.infra.message;

import com.header.correlation.biz.OtherStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class OtherStringGenImpl implements OtherStringGenerator {

    private RestTemplate restTemplate;

    @Autowired
    public OtherStringGenImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getCharA() {
        final String url = "http://localhost:8001/as/api/char";
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        ResponseEntity<CharRes> responseEntity = restTemplate.exchange(requestEntity, CharRes.class);
        return responseEntity.getBody().getCharInfo();
    }

    @Override
    public String getCharC() {
        final String url = "http://localhost:8003/cs/api/char";
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(url))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        ResponseEntity<CharRes> responseEntity = restTemplate.exchange(requestEntity, CharRes.class);
        return responseEntity.getBody().getCharInfo();
    }
}
