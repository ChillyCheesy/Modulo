package com.chillycheesy.modulo.utils.exception;

import com.chillycheesy.modulo.modules.Module;

public class PageIndexAlreadyUsedException extends Exception {

    public PageIndexAlreadyUsedException(Module module) {
        super(module.getName() + ": The page you're trying to submit use an index already used by another page of the module.");
    }
}
