package fr.owle.hometracker.utils.exceptions;

import fr.owle.hometracker.utils.exception.InvalidModuleConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("The configuration module in the file " + file.getAbsolutePath() + " is missing the following field(s) " + Arrays.toString(missingField),
                new InvalidModuleConfigurationException(file, missingField).getMessage());
    }
}
