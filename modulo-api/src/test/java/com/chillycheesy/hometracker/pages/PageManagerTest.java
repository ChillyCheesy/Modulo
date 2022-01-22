package com.chillycheesy.hometracker.pages;

import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.PageMissingIndexAnnotationException;
import com.chillycheesy.hometracker.utils.exception.PageNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PageManagerTest {

   /* class PageMock1 implements Page { }
    @Index("mock")
    class PageMock2 implements Page { }

    private PageManager pageManager;
    private Page page1, page2;
    private Module module1, module2;

    @BeforeEach
    public final void init() throws NoSuchFieldException {
        pageManager = mock(PageManager.class);
        page1 = mock(Page.class);
        page2 = mock(Page.class);
        module1 = mock(Module.class);
        module2 = mock(Module.class);
        final Map<Module, Page> pages = new HashMap<>();
        pages.put(module1, page1);
        pages.put(module1, page2);
        FieldSetter.setField(pageManager, PageManager.class.getDeclaredField("pages"), pages);
    }

    @Test
    public final void doesModuleHavePagesTest() {
        when(pageManager.doesModuleHavePages(any(Module.class))).thenCallRealMethod();
        assertTrue(pageManager.doesModuleHavePages(module1));
        assertFalse(pageManager.doesModuleHavePages(module2));
    }

    @Test
    public final void getPageTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        when(pageManager.getIndexByPage(eq(page1))).thenReturn("page1");
        when(pageManager.getIndexByPage(eq(page2))).thenReturn("page2");

        when(pageManager.getModulePages(any())).thenReturn(new ArrayList<>(Arrays.asList(page1, page2)));
        when(pageManager.getPage(any(), anyString())).thenCallRealMethod();

        assertEquals(page1, pageManager.getPage(module1, "page1"));
        assertEquals(page2, pageManager.getPage(module1, "page2"));
    }

    @Test
    public final void submitPagesTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = new PageManager();
        pageManager.submitPages(module1, page1, page2);
        final Map<Module, List<Page>> map = pageManager.getPagesCopy();
        assertTrue(map.containsKey(module1));
        assertTrue(map.get(module1).contains(page1));
        assertTrue(map.get(module1).contains(page2));
    }

    @Test
    public final void removePageTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = new PageManager();
        pageManager.submitPages(module1, page1, page2);
        pageManager.removePage(module1, page1);
        final Map<Module, List<Page>> map = pageManager.getPagesCopy();
        assertTrue(map.containsKey(module1));
        assertFalse(map.get(module1).contains(page1));
        assertTrue(map.get(module1).contains(page2));
    }

    @Test
    public final void removePagesTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = new PageManager();
        pageManager.submitPages(module1, page1, page2);
        pageManager.removeAllPages(module1);
        final Map<Module, List<Page>> map = pageManager.getPagesCopy();
        assertFalse(map.containsKey(module1));
    }

    @Test
    public final void getIndexByNameTest() throws PageMissingIndexAnnotationException {
        when(pageManager.getIndexByPage(any(Page.class))).thenCallRealMethod();
        assertThrows(PageMissingIndexAnnotationException.class, () -> pageManager.getIndexByPage(new PageMock1()));
        assertEquals("mock", pageManager.getIndexByPage(new PageMock2()));
    }

    @Test
    public final void pageExistTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = new PageManager();
        pageManager.submitPages(module1, page1);
        assertTrue(pageManager.pageExist(module1, page1));
        assertFalse(pageManager.pageExist(module1, page2));
    }*/
}
