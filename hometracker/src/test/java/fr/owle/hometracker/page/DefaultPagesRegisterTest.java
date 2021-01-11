package fr.owle.hometracker.page;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.pages.PageManager;
import fr.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import fr.owle.hometracker.utils.exception.PageNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultPagesRegisterTest {

    @Test
    public final void registerTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = mock(PageManager.class, CALLS_REAL_METHODS);
        final HTAPI htapi = mock(HTAPI.class);
        final DefaultPagesRegisterer defaultPagesRegisterer = new DefaultPagesRegisterer(pageManager);

        doNothing().when(pageManager).submitPages(eq(htapi), any(UiPage.class), any(HTModulePage.class));
        defaultPagesRegisterer.register(htapi);
        verify(pageManager, times(1)).submitPages(eq(htapi), any(UiPage.class), any(HTModulePage.class));
    }

    @Test
    public final void registerDefaultPageManagerTest() {
        final DefaultPagesRegisterer defaultPagesRegisterer = new DefaultPagesRegisterer();
        assertEquals(HTAPI.getPage().getPageManager(), defaultPagesRegisterer.getPageManager());
    }

}
