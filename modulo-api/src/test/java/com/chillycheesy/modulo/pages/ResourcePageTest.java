package com.chillycheesy.modulo.pages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourcePageTest {

    @Test
    public void testGetPath() {
        final ResourcePage resourcePage = new ResourcePage();
        resourcePage.setPath("/test");
        assertEquals(resourcePage.getPath(), "/test/?**");
    }

    @Test
    public void testGetPath2() {
        final ResourcePage resourcePage = new ResourcePage();
        resourcePage.setPath("test");
        assertEquals(resourcePage.getPath(), "/test/?**");
    }

    @Test
    public void testGetPath3() {
        final ResourcePage resourcePage = new ResourcePage();
        resourcePage.setPath("longer/path/test");
        assertEquals(resourcePage.getPath(), "/longer/path/test/?**");
    }

    @Test
    public void testGetResourcePath() {
        final ResourcePage resourcePage = new ResourcePage();
        resourcePage.setPath("/test");
        resourcePage.setResourcePath("/test");
        assertEquals(resourcePage.getResourcePath("/test"), "/test");
    }

    @Test
    public void testGetResourcePath2() {
        final ResourcePage resourcePage = new ResourcePage();
        resourcePage.setPath("/my/test");
        resourcePage.setResourcePath("/public");
        assertEquals(resourcePage.getResourcePath("/my/test"), "/public");
    }

    @Test
    public void testGetResourcePath3() {
        final ResourcePage resourcePage = new ResourcePage();
        resourcePage.setPath("/my/test");
        resourcePage.setResourcePath("/public");
        assertEquals(resourcePage.getResourcePath("/my/test/plus/more/path"), "/public/plus/more/path");
    }

}
