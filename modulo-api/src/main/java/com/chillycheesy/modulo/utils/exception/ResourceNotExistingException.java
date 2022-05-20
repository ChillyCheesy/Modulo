package com.chillycheesy.modulo.utils.exception;

public class ResourceNotExistingException extends Exception {

    public ResourceNotExistingException(String resource, String path) {
        super("You try to get the " + resource + path + " file, but it not existing.");
    }

}
