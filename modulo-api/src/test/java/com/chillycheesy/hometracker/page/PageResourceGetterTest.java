package com.chillycheesy.hometracker.page;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.pages.Page;
import com.chillycheesy.hometracker.pages.subpages.ResourcePage;
import com.chillycheesy.hometracker.utils.exception.HTModuleNotFoundException;
import com.chillycheesy.hometracker.utils.exception.MissingDependenciesModuleException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PageResourceGetterTest {

    private Module module;

    @BeforeEach
    public void buildJarFileModule() throws IOException, HTModuleNotFoundException, MissingDependenciesModuleException {
        ModuloAPI.getModule().getModuleLoader().loadModule(getClass().getClassLoader().getResource("TestModule-1.0.jar").getFile());
        ModuloAPI.getModule().getModuleLoader().startModules();
        this.module = ModuloAPI.getModule().getModuleManager().getModule("TestModule");
    }

    @AfterEach
    public void stopModule() {
        ModuloAPI.getModule().getModuleManager().stopAllModules();
    }

    @Test
    public void moduleIsLoadedTest() {
        assertNotNull(this.module);
    }

    @Test
    public void stringSinglePageContentTest() {
        final Page page = new Page("page", () -> "Hello World");
        assertEquals("Hello World", page.getContent());
    }

    @Test
    public void stringSubPageContentTest() {
        final Page subpage1 = new Page("a", () -> "I m a subpage");
        final Page subpage2 = new Page("b", () -> "me too");
        final Page page = new Page("page", () -> "Hello World").addSubPage(subpage1).addSubPage(subpage2);
        assertEquals("Hello World", page.getContent());
        assertEquals("I m a subpage", subpage1.getContent());
        assertEquals("me too", subpage2.getContent());
    }

    @Test
    public void resourceSinglePageTest() {
        final Page page = new ResourcePage("page", () -> "pageweb");
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        final String expectedHtml = "test";
        assertEquals(Base64.getEncoder().encodeToString(expectedHtml.getBytes()), page.getContent());
    }

    @Test
    public void resourceSubPageTest() {
        final Page subpage = new ResourcePage("a", () -> "subpage");
        final Page subpage2 = new ResourcePage("b", () -> "nothtml");
        final Page page = new ResourcePage("page", () -> "pageweb").addSubPage(subpage.addSubPage(subpage2));
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        assertEquals(Base64.getEncoder().encodeToString("test".getBytes()), page.getContent());
        assertEquals(Base64.getEncoder().encodeToString("subpage".getBytes()), subpage.getContent());
        assertEquals(Base64.getEncoder().encodeToString("nothtml".getBytes()), subpage2.getContent());
    }
}
