package com.chillycheesy.modulo.utils.exceptions;

import com.chillycheesy.modulo.utils.exception.InvalidModuleConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

public class InvalidModuleConfigurationExceptionTest {

    File file;
    String[] missingField = {
            "name",
            "main",
            "authors"
    };

    @BeforeEach
    void setUp() {
        file = new File("./wrong-configuration.yml");
    }

    @Test
    void messageTest() {
        Assertions.assertEquals("The configuration module in the file " + file.getAbsolutePath() + " is missing the following field(s) " + Arrays.toString(missingField),
                new InvalidModuleConfigurationException(file, missingField).getMessage());
    }
}
