package com.chillycheesy.hometracker.pages.exception;

public class No404AccessorException extends Exception{
    public No404AccessorException() {
        super("No accessor with subpath \"*\" found.");
    }
}
