package com.chillycheesy.modulo.utils.exception;

import com.chillycheesy.modulo.modules.Module;

public class PageNotFoundException extends Exception {
    public PageNotFoundException(Module module, String pageIndex) {
        super("Page " + pageIndex + " can't be found in the " + module.getName() + " module.");
    }
}
