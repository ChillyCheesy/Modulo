package com.chillycheesy.hometracker.page;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;
import com.chillycheesy.hometracker.pages.PageManager;
import com.chillycheesy.hometracker.pages.subpages.BooleanRegexPage;
import com.chillycheesy.hometracker.pages.subpages.NumberRegexPage;
import com.chillycheesy.hometracker.pages.subpages.RegexPage;
import com.chillycheesy.hometracker.pages.subpages.StringRegexPage;
import com.chillycheesy.hometracker.utils.exception.No404SubPageException;
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
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/a/b");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkBeforeEndTest() throws No404SubPageException {
        final Page expectedPage = new Page("a");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/a/b/c/d/e/f");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkWithoutSubPageTest() throws No404SubPageException {
        final Page parent = new Page("page");
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/a/b/c/d/e/f");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionFailTest() throws No404SubPageException {
        assertThrows(No404SubPageException.class, () -> {
            ModuloAPI.getPage().getPageManager().redirect(HttpRequest.GET, "notfound/a/b/c");
        });
    }

    @Test
    public void redirectionFindWithHttpRequestTest() throws No404SubPageException {
        final Page expectedPage = new Page(HttpRequest.POST, "a");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.POST, "page/a");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionNotFindWithHttpRequestTest() throws No404SubPageException {
        final Page parent = new Page("page").addSubPage(new Page(HttpRequest.POST, "a"));
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/a");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionWithWeirdSlashTest() throws No404SubPageException {
        final Page expectedPage = new Page("a");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        Page redirectedPage = pm.redirect(HttpRequest.GET, "page/a/");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequest.GET, "/page/a");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWithRegexTest() throws No404SubPageException {
        final Page expectedPage = new RegexPage("\\d+");
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/59");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkWithStringRegexTest() throws No404SubPageException {
        final Page expectedPage = new StringRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/truc");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionWorkWithNumberRegexTest() throws No404SubPageException {
        final Page expectedPage = new NumberRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        Page redirectedPage = pm.redirect(HttpRequest.GET, "page/6");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequest.GET, "page/69");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequest.GET, "page/-42");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequest.GET, "page/3.14");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequest.GET, "page/-3.14");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionFailWithNumberRegexTest() throws No404SubPageException {
        final Page expectedPage = new NumberRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/notanumber");
        assertEquals(parent, redirectedPage);
    }

    @Test
    public void redirectionWorkWithBooleanRegexTest() throws No404SubPageException {
        final Page expectedPage = new BooleanRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        Page redirectedPage = pm.redirect(HttpRequest.GET, "page/true");
        assertEquals(expectedPage, redirectedPage);
        redirectedPage = pm.redirect(HttpRequest.GET, "page/false");
        assertEquals(expectedPage, redirectedPage);
    }

    @Test
    public void redirectionFailWithBooleanRegexTest() throws No404SubPageException {
        final Page expectedPage = new BooleanRegexPage();
        final Page parent = new Page("page").addSubPage(expectedPage);
        final PageManager pm = ModuloAPI.getPage().getPageManager();
        pm.registerItem(null, parent);
        final Page redirectedPage = pm.redirect(HttpRequest.GET, "page/notaboolean");
        assertEquals(parent, redirectedPage);
    }
}
