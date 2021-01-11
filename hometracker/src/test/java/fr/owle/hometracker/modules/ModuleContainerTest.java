package fr.owle.hometracker.modules;

import fr.owle.hometracker.HTAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleContainerTest {

    @Test
    public final void testSingletonGetters() {
        final ModuleContainer moduleContainer = HTAPI.getModule();

        final ModuleManager moduleManager = moduleContainer.getModuleManager();
        final ModuleLoader moduleLoader = moduleContainer.getModuleLoader();

        assertEquals(moduleManager, moduleContainer.getModuleManager());
        assertEquals(moduleLoader, moduleContainer.getModuleLoader());
    }

}
