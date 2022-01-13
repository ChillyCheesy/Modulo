package com.owle.hometracker.modules;

import com.owle.hometracker.ModuloAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuleContainerTest {

    @Test
    public final void testSingletonGetters() {
        final ModuleContainer moduleContainer = ModuloAPI.getModule();

        final ModuleManager moduleManager = moduleContainer.getModuleManager();
        final ModuleLoader moduleLoader = moduleContainer.getModuleLoader();

        assertEquals(moduleManager, moduleContainer.getModuleManager());
        assertEquals(moduleLoader, moduleContainer.getModuleLoader());
    }

}
