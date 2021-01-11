package fr.owle.hello.pages;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.pages.PageManager;
import fr.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import fr.owle.hometracker.utils.exception.PageNotFoundException;

public class PageController {

    public void loadPages(HTModule module) throws PageMissingIndexAnnotationException, PageNotFoundException {
        final PageManager pageManager = HTAPI.getPage().getPageManager();
        final HelloPage helloPage = new HelloPage();

        pageManager.submitPages(module, helloPage);
    }

}
