package fr.owle.hometracker.page;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.pages.PageManager;
import fr.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import fr.owle.hometracker.utils.exception.PageNotFoundException;

public class DefaultPagesRegisterer {

    private final PageManager pageManager;

    public DefaultPagesRegisterer() {
        this(HTAPI.getPage().getPageManager());
    }

    public DefaultPagesRegisterer(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public void register(HTAPI htapi) throws PageMissingIndexAnnotationException, PageNotFoundException {
        final UiPage uiPage = new UiPage();
        final HTModulePage htModulePage = new HTModulePage();

        pageManager.submitPages(htapi, uiPage, htModulePage);
    }

    public PageManager getPageManager() {
        return pageManager;
    }
}
