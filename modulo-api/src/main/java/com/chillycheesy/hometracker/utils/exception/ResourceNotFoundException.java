package com.chillycheesy.hometracker.utils.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String pageIndex) {
        super("You try to load resources from " + pageIndex + " but this page doesn't have @Resource Annotation.");
    }

}
