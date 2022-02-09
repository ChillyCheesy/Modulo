package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.PageManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PageContainerTest {

    @Test
    public final void pageManagerTest() {
        final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        assertEquals(pageManager, ModuloAPI.getPage().getPageManager());
        assertNotNull(pageManager);
    }

}
