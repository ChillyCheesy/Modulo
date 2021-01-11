package fr.owle.hometracker.pages;

import fr.owle.hometracker.HTAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PageContainerTest {

    @Test
    public final void pageManagerTest() {
        final PageManager pageManager = HTAPI.getPage().getPageManager();
        assertEquals(pageManager, HTAPI.getPage().getPageManager());
        assertNotNull(pageManager);
    }

}
