package com.chillycheesy.modulo.utils.exception;

public class PageMissingIndexAnnotationException extends Exception {

    public PageMissingIndexAnnotationException() {
        super("A page you're trying to get is missing the index annotation.");
    }
}
