package com.chillycheesy.sandbox.pages;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageManager;
import com.chillycheesy.modulo.pages.response.ParameterResponse;
import com.chillycheesy.sandbox.SandBoxModule;

public class SandBoxPageManager {

    private final SandBoxModule module;

    public SandBoxPageManager(SandBoxModule module)  {
        this.module = module;
    }

    public void loadPages() {
        final PageManager pageManager = ModuloAPI.getPage().getPageManager();
        final Page upperCasePage = new Page("uppercase", "/uppercase", new ParameterResponse((requestParameters) -> {
            final String name = requestParameters.getParameter("name");
            return name.toUpperCase();
        }));
        pageManager.registerItem(module, upperCasePage);
    }

}
