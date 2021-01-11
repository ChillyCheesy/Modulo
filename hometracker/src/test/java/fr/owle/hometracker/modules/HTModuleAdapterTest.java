package fr.owle.hometracker.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class HTModuleAdapterTest {
    private HTModule module;
    private HTModuleConfig config;

    @BeforeEach
    public final void init() throws NoSuchMethodException, NoSuchFieldException {
        module = mock(HTModuleAdapter.class, CALLS_REAL_METHODS);
        Method load = module.getClass().getDeclaredMethod("onLoad");
        Method start = module.getClass().getDeclaredMethod("onStart");
        Method stop = module.getClass().getDeclaredMethod("onStop");
        config = new HTModuleConfig("MockModule", "0.0.0", List.of("Owl-e"), new ArrayList<>(), new ArrayList<>(), null, null);
        FieldSetter.setField(module, HTModule.class.getDeclaredField("config"), config);
        load.setAccessible(true);
        start.setAccessible(true);
        stop.setAccessible(true);
    }

    @Test
    public final void onLoadTest() throws Exception {
        verify(module, never()).onLoad();
        module.load();
        verify(module, times(1)).onLoad();
    }

    @Test
    public final void onStartTest() throws Exception {
        verify(module, never()).onStart();
        module.start();
        verify(module, times(1)).onStart();
    }

    @Test
    public final void onStopTest() throws Exception {
        verify(module, never()).onStop();
        module.stop();
        verify(module, times(1)).onStop();
    }

    @Test
    public final void configTest(){
        assertEquals(config, module.getConfig());
        assertEquals(config.getAuthors(), module.getAuthors());
        assertEquals(config.getDependencies(), module.getDependencies());
        assertEquals(config.getSoftDependencies(), module.getSoftDependencies());
        assertEquals(config.getName(), module.getName());
        assertEquals(config.getVersion(), module.getVersion());
        assertEquals(config.getMain(), module.getMain());
    }

    @Test
    public final void setterTest(){
        HTModuleConfig newConfig = new HTModuleConfig("Test", "1.0.0", List.of("No","One"), List.of(module.getName()), List.of(module.getName()), "main", null);
        module.setVersion(newConfig.getVersion());
        module.setName(newConfig.getName());
        module.setDependencies(newConfig.getDependencies());
        module.setSoftDependencies(newConfig.getSoftDependencies());
        module.setAuthors(newConfig.getAuthors());
        module.setMain(newConfig.getMain());
        assertEquals(newConfig, module.getConfig());
        assertEquals(newConfig.getAuthors(), module.getAuthors());
        assertEquals(newConfig.getDependencies(), module.getDependencies());
        assertEquals(newConfig.getSoftDependencies(), module.getSoftDependencies());
        assertEquals(newConfig.getName(), module.getName());
        assertEquals(newConfig.getVersion(), module.getVersion());
        assertEquals(newConfig.getMain(), module.getMain());

    }

    @Test
    public final void configGetterSetterTest() {
        HTModuleConfig newConfig = new HTModuleConfig("Test", "1.0.0", List.of("No","One"), List.of(module.getName()), List.of(module.getName()), "main", null);

        module.setConfig(newConfig);
        assertEquals(newConfig, module.getConfig());
        assertEquals(newConfig.getAuthors(), module.getAuthors());
        assertEquals(newConfig.getDependencies(), module.getDependencies());
        assertEquals(newConfig.getSoftDependencies(), module.getSoftDependencies());
        assertEquals(newConfig.getName(), module.getName());
        assertEquals(newConfig.getVersion(), module.getVersion());
        assertEquals(newConfig.getMain(), module.getMain());
    }

    @Test
    public final void constructorTest() {
        HTModuleAdapter m = new HTModuleAdapter(config) {
            @Override
            protected void onLoad() {

            }

            @Override
            protected void onStart() {

            }

            @Override
            protected void onStop() {

            }
        };
        assertEquals(config, m.getConfig());
        m = new HTModuleAdapter("MockModule", "0.0.0", List.of("Owl-e"), new ArrayList<>(), new ArrayList<>(), null) {
            @Override
            protected void onLoad() {

            }

            @Override
            protected void onStart() {

            }

            @Override
            protected void onStop() {

            }
        };
        assertEquals(config, m.getConfig());
        m = new HTModuleAdapter() {
            @Override
            protected void onLoad() {

            }

            @Override
            protected void onStart() {

            }

            @Override
            protected void onStop() {

            }
        };
        assertEquals(new HTModuleConfig(), m.getConfig());
    }
}
