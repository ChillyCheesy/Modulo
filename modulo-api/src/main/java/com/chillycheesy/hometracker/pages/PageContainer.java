package com.chillycheesy.hometracker.pages;

public class PageContainer {
    private static PageManager pageManager;

    public PageManager getPageManager(){
        return pageManager = pageManager == null ? new PageManager() : pageManager;
    }
}
