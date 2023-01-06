package com.chillycheesy.modulo.pages;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageTest {

    @Test
    public void testIsMatch() {
        Page page = new Page();
        page.setPath("/test");
        page.setMethod("GET");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test");
        assertTrue(page.isMatch(request));
    }

    @Test
    public void testIsMatch2() {
        Page page = new Page();
        page.setPath("/test/of/multiple/paths");
        page.setMethod("GET");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test/of/multiple/paths");
        assertTrue(page.isMatch(request));
    }

    @Test
    public void testIsMatch3() {
        Page page = new Page();
        page.setPath("/test/of/multiple/paths");
        page.setMethod("GET");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test/of/multiple");
        assertFalse(page.isMatch(request));
    }

    @Test
    public void testIsMatchWithMethod() {
        Page page = new Page();
        page.setPath("/test");
        page.setMethod("GET");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/test");
        assertTrue(page.isMatch(request));
    }

    @Test
    public void testIsMatchWithMethod2() {
        Page page = new Page();
        page.setPath("/test");
        page.setMethod("POST");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/test");
        assertTrue(page.isMatch(request));
    }

    @Test
    public void testIsMatchWithMethod3() {
        Page page = new Page();
        page.setPath("/test");
        page.setMethod("GET");
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/test");
        assertFalse(page.isMatch(request));
    }

    @Test
    public void testPathReformat() {
        final Page page = new Page();
        page.setPath("/test");
        assertEquals("/test", page.getPath());
    }

    @Test
    public void testPathReformat2() {
        final Page page = new Page();
        page.setPath("test");
        assertEquals("/test", page.getPath());
    }

    @Test
    public void testPathReformat3() {
        final Page page = new Page();
        page.setPath("test/of/multiple/paths");
        assertEquals("/test/of/multiple/paths", page.getPath());
    }

}
