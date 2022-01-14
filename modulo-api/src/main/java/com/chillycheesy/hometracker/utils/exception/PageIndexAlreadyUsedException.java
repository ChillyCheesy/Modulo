package com.chillycheesy.hometracker.utils.exception;

import com.chillycheesy.hometracker.modules.Module;

public class PageIndexAlreadyUsedException extends Exception {

    public PageIndexAlreadyUsedException(Module module) {
        super(module.getName() + ": The page you're trying to submit use an index already used by another page of the module.");
    }
}
