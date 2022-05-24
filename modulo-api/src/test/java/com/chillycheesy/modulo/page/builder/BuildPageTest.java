package com.chillycheesy.modulo.page.builder;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.builder.HttpRequest;
import com.chillycheesy.modulo.pages.builder.PageBuilder;
import com.chillycheesy.modulo.pages.builder.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Disabled
public class BuildPageTest {

    public static class MyHelloPage1 {
        @HttpRequest(type = HttpRequestType.GET, path = "hello/{name}")
        public String hello(@Path("name") String value) {
            return "hello, " + value;
        }

    }

    public static class MyHelloPage2 {

        @HttpRequest(type = HttpRequestType.GET, path = "hello/{value}")
        public String hello(@Path String name) {
            return "hello, " + name;
        }

    }

    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    public final void initRequestMock() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @BeforeEach
    public final void initResponseMock() {

    }

    @Test
    public final void testBuildHello1() throws IOException {
        final Page page = PageBuilder.build(new MyHelloPage1());
        final String path = "/hello/world";
        final Page redirect = page.redirect(HttpRequestType.GET, path);
        when(request.getRequestURI()).thenReturn(path);
        final String content = redirect.applyRequest(request, response,false);
        assertEquals("hello, world", content);
    }

    @Test
    public final void testBuildHello2() throws IOException {
        final Page page = PageBuilder.build(new MyHelloPage2());
        final String path = "/hello/world";
        final Page redirect = page.redirect(HttpRequestType.GET, path);
        when(request.getRequestURI()).thenReturn(path);
        final String content = redirect.applyRequest(request, response,false);
        assertEquals("hello, world", content);
    }


}
