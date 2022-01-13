package com.owle.hometracker.modules;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.utils.exception.FileIsNotAModuleDirectoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModuleLoaderTest {

    private ModuleLoader loader;
    private File folder;
    private HTModuleConfig config1, config2;

    @BeforeEach
    public final void init() throws URISyntaxException {
        loader = new ModuleLoader();
        folder = new File(Objects.requireNonNull(ModuleLoaderTest.class.getClassLoader().getResource("modules")).toURI().getPath());
        config1 = new HTModuleConfig("MockModule1", "0.0.0", List.of("Owl-e"), Collections.singletonList("MockModule2"), new ArrayList<>(), null, null);
        config2 = new HTModuleConfig("MockModule2", "0.0.0", List.of("Owl-e"), new ArrayList<>(), new ArrayList<>(), null, null);
    }

    @Test
    public final void startModulesTest() throws NoSuchFieldException {
        final Module module1 = mock(Module.class, CALLS_REAL_METHODS);
        final Module module2 = mock(Module.class, CALLS_REAL_METHODS);
        FieldSetter.setField(module1, Module.class.getDeclaredField("config"), config1);
        FieldSetter.setField(module2, Module.class.getDeclaredField("config"), config2);
        loader.loadModules(module1, module2);
        assertEquals(0, loader.getStartedModule().size());
        loader.startModules();
        verify(module1, times(1)).start();
        verify(module2, times(1)).start();
        assertEquals(2, loader.getStartedModule().size());
    }

    @Test
    public final void startModulesButMissingDependencies() {
        final HTModuleConfig config = new HTModuleConfig(config1);
        config.setDependencies(Collections.singletonList("depend-module"));
        final Module module = mock(Module.class, CALLS_REAL_METHODS);
        module.setConfig(config);
        assertEquals(0, loader.getStartedModule().size());
        loader.loadModule(module);
        loader.startModules();
        assertEquals(0, loader.getStartedModule().size());
    }

    @Test
    public final void loadModuleFileTest() throws IOException {
        final String jar = folder.getAbsolutePath() + "/hello-JARJARBINKS-1.0.0.jar";
        assertEquals(0, loader.getLoadedModules().size());
        loader.loadModule(jar);
        assertEquals(1, loader.getLoadedModules().size());
    }

    @Test
    public final void loadModulesFileTest() throws FileIsNotAModuleDirectoryException, IOException {
        assertEquals(0, loader.getLoadedModules().size());
        loader.loadModules(folder.getAbsolutePath());
        assertEquals(1, loader.getLoadedModules().size());
    }

    @Test
    public final void loadModulesTest() {
        final Module module1 = mock(Module.class, CALLS_REAL_METHODS);
        final Module module2 = mock(Module.class, CALLS_REAL_METHODS);
        assertEquals(0, loader.getLoadedModules().size());
        loader.loadModules(module1, module2);
        verify(module1, times(1)).load();
        verify(module2, times(1)).load();
        assertEquals(2, loader.getLoadedModules().size());
    }

    @Test
    public final void isValidTest() throws IOException {
        final String jar = folder.getAbsolutePath() + "/hello-JARJARBINKS-1.0.0.jar";
        final String badJar = folder.getAbsolutePath() + "/bad-hello-JARJARBINKS-1.0.0.jar";
        assertTrue(loader.isValid(jar));
        assertFalse(loader.isValid(badJar));
        try {
            loader.isValid("");
            fail("Should throw exception");
        } catch (IOException ignored) { }
    }

    @AfterEach
    public final void afterAll(){
        ModuloAPI.getModule().getModuleManager().removeModule("MockModule1", "MockModule2", "Hello");
    }

}
