package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.modules.HTModule;

public class PageNotFoundException extends Exception {
    public PageNotFoundException(HTModule module, String pageIndex) {
        super("Page " + pageIndex + " can't be found in the " + module.getName() + " module.");
    }
}
