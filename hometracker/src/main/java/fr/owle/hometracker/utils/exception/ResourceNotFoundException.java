package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.pages.Page;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String pageIndex) {
        super("You try to load resources from " + pageIndex + " but this page doesn't have @Resource Annotation.");
    }

}
