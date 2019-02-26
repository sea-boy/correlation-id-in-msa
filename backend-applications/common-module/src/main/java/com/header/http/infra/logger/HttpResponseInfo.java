package com.header.http.infra.logger;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpResponseInfo {
    private HttpStatus httpStatus;
    private String body;

    @Builder
    private HttpResponseInfo(HttpStatus httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }
}
