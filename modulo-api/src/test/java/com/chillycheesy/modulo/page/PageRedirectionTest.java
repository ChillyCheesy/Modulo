package com.chillycheesy.modulo.page;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.pages.natif.BooleanPage;
import com.chillycheesy.modulo.pages.natif.NumberRegexPage;
import com.chillycheesy.modulo.pages.natif.RegexPage;
import com.chillycheesy.modulo.pages.natif.StringRegexPage;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageRedirectionTest {

    @AfterEach
    public void clearPageManager() {
        ModuloAPI.getPage().getPageManager().removeAllItems(null);
    }

    @Test
    public void redirectionWorkTest() throws No404SubPageException {
        final Page expectedPage = new Page("b");
        final Page parent = new Page("page").addSubPage(new Page("a").addSubPage(expectedPage));
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/a/b");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkBeforeEndTest() throws No404SubPageException {
        final Page expectedPage = new Page("a");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/a/b/c/d/e/f");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkWithoutSubPageTest() throws No404SubPageException {
        final Page parent = new Page("page");
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/a/b/c/d/e/f");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionFailTest() throws No404SubPageException {
        assertThrows(No404SubPageException.class, () -> {
            ModuloAPI.getPage().getPageManager().redirect(HttpRequestType.GET, "notfound/a/b/c");
        });
    }

    @Test
    public void redirectionFindWithHttpRequestTest() throws No404SubPageException {
        final Page expectedPage = new Page(HttpRequestType.POST, "a");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.POST, "page/a");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionNotFindWithHttpRequestTest() throws No404SubPageException {
        final Page parent = new Page("page").addSubPage(new Page(HttpRequestType.POST, "a"));
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/a");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionWithWeirdSlashTest() throws No404SubPageException {
        final Page expectedPage = new Page("a");
        final Page parent = new Page("page");
        parent.addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/a/");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "/page/a");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "/page");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionWithRegexTest() throws No404SubPageException {
        final Page expectedPage = new RegexPage("\\d+");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/59");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkWithStringRegexTest() throws No404SubPageException {
        final Page expectedPage = new StringRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/truc");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkWithNumberRegexTest() throws No404SubPageException {
        final Page expectedPage = new NumberRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/6");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "page/69");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "page/-42");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "page/3.14");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "page/-3.14");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionFailWithNumberRegexTest() throws No404SubPageException {
        final Page expectedPage = new NumberRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/notanumber");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionWorkWithBooleanRegexTest() throws No404SubPageException {
        final Page expectedPage = new BooleanPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/true");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequestType.GET, "page/false");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionFailWithBooleanRegexTest() throws No404SubPageException {
        final Page expectedPage = new BooleanPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequestType.GET, "page/notaboolean");
        assertEquals(parent, redirectedPage);
    }
}
