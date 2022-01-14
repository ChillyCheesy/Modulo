package com.chillycheesy.hometracker.utils.exception;

import java.io.File;

public class FileIsNotAModuleDirectoryException extends Exception {

    public FileIsNotAModuleDirectoryException(File file) {
        super("The file " + file.getAbsolutePath() + " is not a module directory");
    }
}
