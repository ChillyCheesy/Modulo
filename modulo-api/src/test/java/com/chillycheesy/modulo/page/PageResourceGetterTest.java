package com.chillycheesy.modulo.page;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.subpages.ResourcePage;
import com.chillycheesy.modulo.utils.exception.HTModuleNotFoundException;
import com.chillycheesy.modulo.utils.exception.MissingDependenciesModuleException;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
        assertEquals(expectedHtml, page.getContent());
    }

    @Test
    public void resourceSubPageTest() {
        final Page subpage = new ResourcePage("a", () -> "subpage");
        final Page subpage2 = new ResourcePage("b", () -> "nothtml");
        final Page page = new ResourcePage("page", () -> "pageweb").addSubPage(subpage.addSubPage(subpage2));
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        assertEquals("test", page.getContent());
        assertEquals("subpage", subpage.getContent());
        assertEquals("nothtml", subpage2.getContent());
    }

    @Test
    public void resourceSubPageByPageManagerRedirectionTest() throws No404SubPageException {
        final Page subpage = new ResourcePage("a", () -> "subpage");
        final Page subpage2 = new ResourcePage("b", () -> "nothtml");
        final Page page = new ResourcePage("page", () -> "pageweb").addSubPage(subpage.addSubPage(subpage2));
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        assertEquals("test", ModuloAPI.getPage().getPageManager().redirect(HttpRequest.GET, "page").getContent());
        assertEquals("subpage", ModuloAPI.getPage().getPageManager().redirect(HttpRequest.GET, "page/a").getContent());
        assertEquals("nothtml", ModuloAPI.getPage().getPageManager().redirect(HttpRequest.GET, "page/a/b").getContent());
    }
}
