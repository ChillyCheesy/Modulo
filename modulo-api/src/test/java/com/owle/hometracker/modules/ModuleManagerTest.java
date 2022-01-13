package com.owle.hometracker.modules;

import com.owle.hometracker.utils.exception.HTModuleNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModuleManagerTest {
    private ModuleManager manager;
    private Module module1, module2;
    private HTModuleConfig config1, config2;

    @BeforeEach
    public final void init() throws NoSuchFieldException {
        manager = new ModuleManager();
        config1 = new HTModuleConfig("module 1", "0.0.0", List.of("Owl-e"), new ArrayList<>(), new ArrayList<>(), null, null);
        config2 = new HTModuleConfig("module 2", "0.0.0", List.of("Owl-e"), new ArrayList<>(), new ArrayList<>(), null, null);
        module1 = mock(Module.class, CALLS_REAL_METHODS);
        FieldSetter.setField(module1, Module.class.getDeclaredField("config"), config1);
        module2 = mock(Module.class, CALLS_REAL_METHODS);
        FieldSetter.setField(module2, Module.class.getDeclaredField("config"), config2);
    }

    @Test
    public final void addModuleTest() {
        assertEquals(0, manager.getModuleCount());
        manager.addModule(module1);
        assertEquals(1, manager.getModuleCount());
        manager.addModule(module1);
        assertEquals(1, manager.getModuleCount());
        manager.addModule(module2);
        assertEquals(2, manager.getModuleCount());
    }

    @Test
    public final void removeModuleTest() {
        manager.addModule(module1);
        manager.addModule(module2);
        assertEquals(2, manager.getModuleCount());
        manager.removeModule(module1);
        assertEquals(1, manager.getModuleCount());
        manager.removeModule(module1);
        assertEquals(1, manager.getModuleCount());
        manager.removeModule("module 2");
        assertEquals(0, manager.getModuleCount());
    }

    @Test
    public final void containsModuleTest() {
        assertFalse(manager.containsModule(module1.getClass()));
        manager.addModule(module1);
        assertFalse(manager.containsModule(module2));
        assertFalse(manager.containsModule("module 2"));
        manager.addModule(module2);
        assertTrue(manager.containsModule(module1));
        assertTrue(manager.containsModule(module2));
        assertTrue(manager.containsModule("module 1"));
        assertTrue(manager.containsModule("module 2"));
        assertFalse(manager.containsModule("not a module"));
        assertTrue(manager.containsModule(module1.getClass()));
        assertTrue(manager.containsModule(module2.getClass()));
    }

    @Test()
    public final void getModuleTest() throws HTModuleNotFoundException {
        Exception e = assertThrows(HTModuleNotFoundException.class, () -> manager.getModule("NoModule"));
        assertEquals(e.getMessage(), new HTModuleNotFoundException("NoModule").getMessage());
        e = assertThrows(HTModuleNotFoundException.class, () -> manager.getModule("module 1"));
        assertEquals(e.getMessage(), new HTModuleNotFoundException("module 1").getMessage());
        manager.addModule(module1);
        assertDoesNotThrow(() -> {
            manager.getModule("module 1");
            manager.getModule(module1.getClass());
        });
        e = assertThrows(HTModuleNotFoundException.class, () -> manager.getModule("module 2"));
        assertEquals(e.getMessage(), new HTModuleNotFoundException("module 2").getMessage());
        manager.addModule(module2);
        assertDoesNotThrow(() -> {
            manager.getModule("module 2");
            manager.getModule(module2.getClass());
        });
    }

    @Test
    public final void stopModuleTest() {
        manager.addModule(module1, module2);
        verify(module1, never()).stop();
        verify(module2, never()).stop();
        manager.stopAllModules();
        verify(module1, times(1)).stop();
        verify(module2, times(1)).stop();
    }

    @Test
    public final void getModulesCopyTest() {
        manager.addModule(module1, module2);
        assertEquals(new ArrayList<>(Arrays.asList(module1, module2)), manager.getModulesCopy());
        manager.getModulesCopy().clear();
        assertEquals(new ArrayList<>(Arrays.asList(module1, module2)), manager.getModulesCopy());
    }
}
