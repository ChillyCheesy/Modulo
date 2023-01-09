package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.ModuloEntity;
import com.chillycheesy.modulo.pages.PageContainer;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.sandbox.SandBoxModule;

public class SandBoxPageManager implements ModuloEntity<SandBoxModule> {

    private SandBoxModule module;
    private PageManager pageManager;

    @Override
    public void load(SandBoxModule module) {
        final PageContainer pageContainer = ModuloAPI.getPage();
        this.pageManager = pageContainer.getPageManager();
        this.module = module;
    }

    @Override
    public void start() {
        pageManager.buildAndRegisterPage(module, new JokePage());
    }

    @Override
    public void stop() {
        pageManager.removeAllItems(module);
    }
}
