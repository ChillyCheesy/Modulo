package com.chillycheesy.modulo.pages;

public class PageContainer {

    private static PageManager pageManager;

    public PageManager getPageManager() {
        return pageManager = pageManager == null ? new PageManager() : pageManager;
    }

}
