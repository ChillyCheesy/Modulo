package fr.owle.hometracker.utils.exception;

import fr.owle.hometracker.modules.HTModule;

public class PageIndexAlreadyUsedException extends Exception {

    public PageIndexAlreadyUsedException(HTModule module) {
        super(module.getName() + ": The page you're trying to submit use an index already used by another page of the module.");
    }
}
