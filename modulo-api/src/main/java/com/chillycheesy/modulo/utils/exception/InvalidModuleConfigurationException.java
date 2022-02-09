package com.chillycheesy.modulo.utils.exception;

import java.io.File;
import java.util.Arrays;

public class InvalidModuleConfigurationException extends Exception {
    public InvalidModuleConfigurationException(File file, String...missingField) {
        super("The configuration module in the file " + file.getAbsolutePath() + " is missing the following field(s) " + Arrays.toString(missingField));
    }
}
