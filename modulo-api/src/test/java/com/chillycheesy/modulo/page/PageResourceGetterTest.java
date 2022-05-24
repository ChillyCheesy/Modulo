package com.chillycheesy.modulo.page;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuleLoader;
import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.natif.ResourcePage;
import com.chillycheesy.modulo.utils.exception.HTModuleNotFoundException;
import com.chillycheesy.modulo.utils.exception.MissingDependenciesModuleException;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Disabled
public class PageResourceGetterTest {

    private Module module;
    private HttpServletResponse response;
    private HttpServletRequest request;

    @BeforeEach
    public void buildJarFileModule() throws IOException, HTModuleNotFoundException, MissingDependenciesModuleException {
        final String file = Objects.requireNonNull(getClass().getClassLoader().getResource("TestModule-1.0.jar")).getFile();
        final ModuleLoader loader = ModuloAPI.getModule().getModuleLoader();
        loader.loadModule(file);
        loader.startModules();
        this.module = ModuloAPI.getModule().getModuleManager().getModule("TestModule");

        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
    }

    @AfterEach
    public void stopModule() {
        ModuloAPI.getModule().getModuleManager().stopAllModules();
        ModuloAPI.getPage().getPageManager().getAllItems().clear();
    }

    @Test
    public void moduleIsLoadedTest() {
        assertNotNull(this.module);
    }

    @Test
    public void stringSinglePageContentTest() throws IOException {
        final Page page = new Page("page","Hello World");
        assertEquals("Hello World", page.applyRequest(request, response, false));
    }

    @Test
    public void stringSubPageContentTest() throws IOException {
        final Page subpage1 = new Page("a","I m a subpage");
        final Page subpage2 = new Page("b","me too");
        final Page page = new Page("page","Hello World").addSubPage(subpage1).addSubPage(subpage2);
        assertEquals("Hello World", page.applyRequest(request, response, false));
        assertEquals("I m a subpage", subpage1.applyRequest(request, response, false));
        assertEquals("me too", subpage2.applyRequest(request, response, false));
    }

    @Test
    public void resourceSinglePageTest() throws IOException {
        final Page page = new ResourcePage("page","pageweb");
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        final String expectedHtml = "pageweb";
        assertEquals(expectedHtml, page.applyRequest(request, response, false));
    }

    @Test
    public void resourceSubPageTest() throws IOException {
        final Page subpage = new ResourcePage("a","subpage");
        final Page subpage2 = new ResourcePage("b","nothtml");
        final Page page = new ResourcePage("page","pageweb").addSubPage(subpage.addSubPage(subpage2));
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        assertEquals("pageweb", page.applyRequest(request, response, false));
        assertEquals("subpage", subpage.applyRequest(request, response, false));
        assertEquals("nothtml", subpage2.applyRequest(request, response, false));
    }

    @Test
    public void resourceSubPageByPageManagerRedirectionTest() throws No404SubPageException, IOException {
        final Page subpage = new ResourcePage("a","subpage");
        final Page subpage2 = new ResourcePage("b","nothtml");
        final Page page = new ResourcePage("page","pageweb").addSubPage(subpage.addSubPage(subpage2));
        ModuloAPI.getPage().getPageManager().registerItem(this.module, page);
        assertEquals("pageweb", ModuloAPI.getPage().getPageManager().redirect(HttpRequestType.GET, "page").applyRequest(request, response, false));
        assertEquals("subpage", ModuloAPI.getPage().getPageManager().redirect(HttpRequestType.GET, "page/a").applyRequest(request, response, false));
        assertEquals("nothtml", ModuloAPI.getPage().getPageManager().redirect(HttpRequestType.GET, "page/a/b").applyRequest(request, response, false));
    }
}
