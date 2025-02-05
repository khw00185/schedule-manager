package com.example.schedulemanager.user.jwt;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedBody = request.getInputStream().readAllBytes(); // 요청 바디를 캐싱
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody);
        return new CachedServletInputStream(byteArrayInputStream);
    }

    private static class CachedServletInputStream extends ServletInputStream {

        private final InputStream inputStream;

        public CachedServletInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public boolean isFinished() {
            try {
                return inputStream.available() == 0;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }
    }
}