package com.owle.hometracker.page;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.pages.PageManager;
import com.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import com.owle.hometracker.utils.exception.PageNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultPagesRegisterTest {

    @Test
    public final void registerTest() throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = mock(PageManager.class, CALLS_REAL_METHODS);
        final ModuloAPI moduloAPI = mock(ModuloAPI.class);
        final DefaultPagesRegisterer defaultPagesRegisterer = new DefaultPagesRegisterer(pageManager);

        doNothing().when(pageManager).submitPages(eq(moduloAPI), any(UiPage.class), any(HTModulePage.class));
        defaultPagesRegisterer.register(moduloAPI);
        verify(pageManager, times(1)).submitPages(eq(moduloAPI), any(UiPage.class), any(HTModulePage.class));
    }

    @Test
    public final void registerDefaultPageManagerTest() {
        final DefaultPagesRegisterer defaultPagesRegisterer = new DefaultPagesRegisterer();
        assertEquals(ModuloAPI.getPage().getPageManager(), defaultPagesRegisterer.getPageManager());
    }

}
