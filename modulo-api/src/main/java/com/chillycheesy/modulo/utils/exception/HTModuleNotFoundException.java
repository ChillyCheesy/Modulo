package com.chillycheesy.modulo.utils.exception;

public class HTModuleNotFoundException extends Exception {

    public HTModuleNotFoundException(String name) {
        super(name + " module can't be found.");
    }
}
