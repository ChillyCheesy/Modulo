package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.pages.ResourcePage;
import com.chillycheesy.sandbox.SandBoxModule;

public class SandBoxPageManager {

    private final SandBoxModule module;

    public SandBoxPageManager(SandBoxModule module)  {
        this.module = module;
    }

    public void loadPages() {
        final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        pageManager.buildAndRegisterPage(module, new JokePage());
    }

}
