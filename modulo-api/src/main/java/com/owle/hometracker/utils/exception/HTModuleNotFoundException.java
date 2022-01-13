package com.owle.hometracker.utils.exception;

public class HTModuleNotFoundException extends Exception {

    public HTModuleNotFoundException(String name) {
        super(name + " module can't be found.");
    }
}
