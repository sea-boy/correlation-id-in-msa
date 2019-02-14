package com.header.correlation.api;

import com.header.correlation.biz.OtherStringGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharAApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OtherStringGenerator osGenerator;

    @Before
    public void setUp() {
        when(osGenerator.getCharB()).thenReturn("b");
        when(osGenerator.getCharC()).thenReturn("c");
    }

    @Test
    public void getChar() {
        // given
        final String url = "/api/char";
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(url))
                .header(MediaType.APPLICATION_JSON_VALUE)
                .build();

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(responseEntity.getStatusCode())
                .as("check http status: %s", responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .as("check response body: %s", responseEntity.getBody())
                .isEqualTo("a");
    }

    @Test
    public void getString() {
        // given
        final String url = "/api/string";
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(url))
                .header(MediaType.APPLICATION_JSON_VALUE)
                .build();

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // then
        assertThat(responseEntity.getStatusCode())
                .as("check http status: %s", responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .as("check response body: %s", responseEntity.getBody())
                .isEqualTo("abc");
    }
}