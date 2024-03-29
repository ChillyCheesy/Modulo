package com.chillycheesy.modulo.config;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockStream {

    public static InputStream fromString(String data) {
        return new InputStream() {
            private int index = 0;
            private int length = data.length();

            @Override
            public int read() throws IOException {
                if (index < length) {
                    return data.charAt(index++);
                } else {
                    return -1;
                }
            }
        };
    }

    public static class MockOutputStream extends OutputStream {
        private final StringBuilder sb = new StringBuilder();

        @Override
        public void write(int b) {
            sb.append((char) b);
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }

    public static class MockServletOutputStream extends ServletOutputStream {
        private final StringBuilder sb = new StringBuilder();

        @Override
        public void write(int b) {
            sb.append((char) b);
        }

        @Override
        public String toString() {
            return sb.toString();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }

}
