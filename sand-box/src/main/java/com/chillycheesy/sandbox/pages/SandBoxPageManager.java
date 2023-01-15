package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuloEntity;
import com.chillycheesy.modulo.pages.PageContainer;
import com.chillycheesy.modulo.pages.PageManager;

public class SandBoxPageManager implements ModuloEntity {

    private Module module;
    private PageManager pageManager;

    @Override
    public void load(Module module) {
        final PageContainer pageContainer = ModuloAPI.getPage();
        this.pageManager = pageContainer.getPageManager();
        this.module = module;
    }

    @Override
    public void start() {
        pageManager.buildAndRegisterPage(module, new JokePage(module));
    }

    @Override
    public void stop() {
        pageManager.removeAllItems(module);
    }
}
