package com.chillycheesy.sandbox;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.sandbox.pages.SandBoxPageManager;

public class SandBoxModule extends Module {

    public static SandBoxModule instance;

    private SandBoxPageManager pageManager;

    @Override
    protected void onLoad() {
        instance = this;
        pageManager = new SandBoxPageManager();
        pageManager.load(this);
    }

    @Override
    protected void onStart() {
        final String message = defaultConfiguration.getString("message");
        info(message);
        pageManager.start();
    }

    @Override
    protected void onStop() {
        pageManager.stop();
    }

}
