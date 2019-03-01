package com.header.http.infra.logger;

import lombok.Builder;
import lombok.Getter;


@Getter
public class HttpResponseInfo {
    private String httpStatus;
    private String body;

    @Builder
    private HttpResponseInfo(String httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }
}
