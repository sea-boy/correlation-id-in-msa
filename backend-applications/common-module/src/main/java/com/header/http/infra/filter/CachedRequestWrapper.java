package com.header.http.infra.filter;



import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;


public class CachedRequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayOutputStream outputStream;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CachedRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (null == outputStream) {
            outputStream = new ByteArrayOutputStream();
            IOUtils.copy(super.getInputStream(), outputStream);
        }
        return new CachedServletInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    class CachedServletInputStream extends ServletInputStream {
        ByteArrayInputStream inputStream;

        CachedServletInputStream() {
            this.inputStream = new ByteArrayInputStream(CachedRequestWrapper.this.outputStream.toByteArray());
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return this.inputStream.read();
        }
    }

}
