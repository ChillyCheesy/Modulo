package com.chillycheesy.modulo.page.builder;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.builder.HttpRequest;
import com.chillycheesy.modulo.pages.builder.PageBuilder;
import com.chillycheesy.modulo.pages.builder.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class BuildPageTest {

    private static class MyHelloPage1 {

        @HttpRequest(type = HttpRequestType.GET, path = "hello/{name}")
        public String hello(@Path(value = "name", defaultValue = "world") String value) {
            return "hello, " + value;
        }

    }

    private static class MyHelloPage2 {

        @HttpRequest(type = HttpRequestType.GET, path = "hello/{name}")
        public String hello(@Path("name") String value) {
            return "hello, " + value;
        }

    }

    private static class MyHelloPage3 {

        @HttpRequest(type = HttpRequestType.GET, path = "hello/{name}")
        public String hello(@Path String name) {
            return "hello, " + name;
        }

    }

    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    public final void initRequestMock() {
        request = mock(HttpServletRequest.class);
    }

    @BeforeEach
    public final void initResponseMock() {
        response = mock(HttpServletResponse.class);
    }

    @Test
    public final void testBuildHello1() throws IOException {
        final Page page = PageBuilder.build(new MyHelloPage1());
        final String content = page.redirect(HttpRequestType.GET, "hello").getContent(request, response);
        assertEquals("hello, world", content);
    }


}
