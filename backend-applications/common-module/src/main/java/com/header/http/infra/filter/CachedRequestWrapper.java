package com.header.http.infra.filter;



import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class CachedRequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayInputStream inputStream;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CachedRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            this.inputStream = new ByteArrayInputStream(IOUtils.toByteArray(request.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }
}
