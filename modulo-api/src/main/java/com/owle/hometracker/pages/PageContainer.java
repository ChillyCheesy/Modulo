package com.owle.hometracker.pages;

public class PageContainer {
    private static PageManager pageManager;

    @Deprecated
    public PageManager getPageManager(){
        return pageManager = pageManager == null ? new PageManager() : pageManager;
    }
}
