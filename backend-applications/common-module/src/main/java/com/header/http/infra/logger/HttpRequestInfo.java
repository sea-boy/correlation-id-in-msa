package com.header.http.infra.logger;


import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.net.URI;

@Getter
public class HttpRequestInfo {
    private HttpMethod httpMethod;
    private URI uri;
    private byte[] body;

    @Builder
    private HttpRequestInfo(HttpMethod httpMethod, URI uri, byte[] body) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.body = body;
    }

}
