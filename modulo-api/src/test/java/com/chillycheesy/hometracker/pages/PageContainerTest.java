package com.chillycheesy.hometracker.pages;

import com.chillycheesy.hometracker.ModuloAPI;
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
