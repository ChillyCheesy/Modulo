package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.pages.Page;

public class PageMissingIndexAnnotationException extends Exception {

    public PageMissingIndexAnnotationException() {
        super("A page you're trying to get is missing the index annotation.");
    }
}
