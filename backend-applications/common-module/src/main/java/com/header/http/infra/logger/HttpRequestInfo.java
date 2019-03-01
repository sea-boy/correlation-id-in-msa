package com.header.http.infra.logger;


import lombok.Builder;
import lombok.Getter;

@Getter
public class HttpRequestInfo {
    private String httpMethod;
    private String uri;
    private String body;

    @Builder
    private HttpRequestInfo(String httpMethod, String uri, String body) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.body = body;
    }

}
