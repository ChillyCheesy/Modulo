package com.chillycheesy.hometracker.utils.exception;

import com.chillycheesy.hometracker.modules.Module;

public class PageNotFoundException extends Exception {
    public PageNotFoundException(Module module, String pageIndex) {
        super("Page " + pageIndex + " can't be found in the " + module.getName() + " module.");
    }
}
