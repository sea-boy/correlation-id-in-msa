package com.header.http.infra.filter;

import org.springframework.util.ResizableByteArrayOutputStream;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class CachedResponseWrapper extends HttpServletResponseWrapper {
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private ResizableByteArrayOutputStream content = new ResizableByteArrayOutputStream(1024);
    private ServletOutputStream outputStream = new CachedResponseWrapper.ServletResponseOutputStream();
    private PrintWriter writer;

    public CachedResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void sendError(int sc) throws IOException {
        this.copyBodyToResponse();
        super.sendError(sc);
    }

    public void sendError(int sc, String msg) throws IOException {
        this.copyBodyToResponse();
        super.sendError(sc, msg);
    }

    public void sendRedirect(String location) throws IOException {
        this.copyBodyToResponse();
        super.sendRedirect(location);
    }

    public ServletOutputStream getOutputStream() {
        return this.outputStream;
    }

    public String getResponseBodyAsString() throws IOException {
        byte[] responseBytes = this.toByteArray();
        if (responseBytes != null && responseBytes.length > 0) {
            HttpServletResponse response = (HttpServletResponse)this.getResponse();
            StreamUtils.copy(responseBytes, response.getOutputStream());
            return new String(responseBytes);
        } else {
            return "";
        }
    }

    public PrintWriter getWriter() throws IOException {
        if (this.writer == null) {
            String characterEncoding = this.getCharacterEncoding();
            this.writer = (characterEncoding != null) ?
                    new CachedResponseWrapper.ResponsePrintWriter(characterEncoding) :
                    new CachedResponseWrapper.ResponsePrintWriter("ISO-8859-1");
        }

        return this.writer;
    }

    public void setContentLength(int len) {
        if (len > this.content.capacity()) {
            this.content.resize(len);
        }
    }

    public void setContentLengthLong(long len) {
        if (len > 2147483647L) {
            throw new IllegalArgumentException("Content-Length exceeds ServletResponse's maximum length (2147483647): " + len);
        } else {
            if (len > (long)this.content.capacity()) {
                this.content.resize((int)len);
            }
        }
    }

    public void setBufferSize(int size) {
        if (size > this.content.capacity()) {
            this.content.resize(size);
        }
    }

    public void resetBuffer() {
        this.content.reset();
    }

    public void reset() {
        super.reset();
        this.content.reset();
    }

    public boolean isCommitted() {
        return super.isCommitted();
    }

    public byte[] toByteArray() {
        return this.content.toByteArray();
    }

    private void copyBodyToResponse() throws IOException {
        if (this.content.size() > 0) {
            this.getResponse().setContentLength(this.content.size());
            StreamUtils.copy(this.content.toByteArray(), this.getResponse().getOutputStream());
            this.content.reset();
        }

    }

    private class ResponsePrintWriter extends PrintWriter {
        public ResponsePrintWriter(String characterEncoding) throws UnsupportedEncodingException {
            super(new OutputStreamWriter(CachedResponseWrapper.this.content, characterEncoding));
        }

        public void write(char[] buf, int off, int len) {
            super.write(buf, off, len);
            super.flush();
        }

        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
        }

        public void write(int c) {
            super.write(c);
            super.flush();
        }
    }

    private class ServletResponseOutputStream extends ServletOutputStream {
        private WriteListener writeListener;

        private ServletResponseOutputStream() {
        }

        public boolean isReady() {
            return true;
        }

        public void setWriteListener(WriteListener writeListener) {
            this.writeListener = writeListener;
        }

        public void write(int b) throws IOException {
            CachedResponseWrapper.this.content.write(b);
            if (this.writeListener != null) {
                this.writeListener.notify();
            }
        }

        public void write(byte[] b, int off, int len) throws IOException {
            CachedResponseWrapper.this.content.write(b, off, len);
            if (this.writeListener != null) {
                this.writeListener.notify();
            }
        }
    }
}
