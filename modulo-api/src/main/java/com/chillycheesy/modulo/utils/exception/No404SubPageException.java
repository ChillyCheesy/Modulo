package com.chillycheesy.modulo.utils.exception;

public class No404SubPageException extends Exception {
    public No404SubPageException() {
        super("No sub page with subpath \"*\" found.");
    }
}
