package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.modules.Module;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PageManagerTest {

    private static PageManager pageManager;
    private static Module module;
    private static Module module2;

    @BeforeAll
    public static void init() {
        pageManager = new PageManager();
        module = mock(Module.class);
        when(module.getName()).thenReturn("TestModule");
        module2 = mock(Module.class);
        when(module2.getName()).thenReturn("TestModule2");
    }

    @Test
    public void testRegisterPage() {
        final Page page = mock(Page.class);
        when(page.getName()).thenReturn("TestPage");
        final Page page2 = mock(Page.class);
        when(page2.getName()).thenReturn("TestPage2");
        final Page page3 = mock(Page.class);
        when(page3.getName()).thenReturn("TestPage");
        pageManager.registerItem(module, page, page2);
        assertEquals(pageManager.getModuleByItem(page), module);
        assertEquals(pageManager.getModuleByItem(page2), module);
        pageManager.registerItem(module2, page3);
        assertFalse(pageManager.getItemByModule(module).contains(page));
        assertEquals(pageManager.getModuleByItem(page3), module2);
    }

    @Test
    public void testResponse() {
        final Page page = mock(Page.class);
        when(page.getName()).thenReturn("TestPage");
        when(page.getPriority()).thenReturn(1);
        when(page.getPath()).thenReturn("test");
        when(page.getPathComparator()).thenReturn(List.of((request, page1) -> true));
        final Page page2 = mock(Page.class);
        when(page2.getName()).thenReturn("TestPage2");
        when(page.getPriority()).thenReturn(2);
        when(page.getPath()).thenReturn("test/longer");
        when(page.getPathComparator()).thenReturn(List.of((request, page1) -> true));
        final Page page3 = mock(Page.class);
        when(page3.getName()).thenReturn("TestPage3");
        when(page.getPriority()).thenReturn(3);
        when(page.getPath()).thenReturn("test/long");
        when(page.getPathComparator()).thenReturn(List.of((request, page1) -> true));
        pageManager.registerItem(module, page, page2);
        pageManager.registerItem(module2, page3);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
    }

    @AfterAll
    public static void end() {
        pageManager.removeAllItems(module);
    }

}
