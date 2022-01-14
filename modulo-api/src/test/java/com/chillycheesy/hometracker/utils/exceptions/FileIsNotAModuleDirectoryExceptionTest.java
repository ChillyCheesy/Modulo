package com.chillycheesy.hometracker.utils.exceptions;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.utils.exception.FileIsNotAModuleDirectoryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileIsNotAModuleDirectoryExceptionTest {

    private File file;

    @BeforeEach
    void setUp() {
        file = new File("./modules/htmodule-ALPHA-0.0.1.jar");
    }

    @Test
    void messageTest() throws FileIsNotAModuleDirectoryException, IOException {
        assertEquals("The file " + file.getAbsolutePath() + " is not a module directory", new FileIsNotAModuleDirectoryException(file).getMessage());
        assertThrows(FileIsNotAModuleDirectoryException.class, () -> ModuloAPI.getModule().getModuleLoader().loadModules(file));
    }
}
