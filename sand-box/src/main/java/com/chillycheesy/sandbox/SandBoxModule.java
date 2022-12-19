package com.chillycheesy.sandbox;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Log;
import com.chillycheesy.sandbox.pages.SandBoxPageManager;

public class SandBoxModule extends Module {

    public static SandBoxModule instance;

    private SandBoxPageManager pageManager;

    @Override
    protected void onLoad() {
        instance = this;
        pageManager = new SandBoxPageManager(this);
    }

    @Override
    protected void onStart() {
        log("hello, world test");
        pageManager.loadPages();
    }

    @Override
    protected void onStop() {

    }

    public void log(String message) {
        final Log logger = ModuloAPI.getLogger();
        logger.info(this, message);
    }
}
