package fr.owle.hometracker.modules;


import fr.owle.hometracker.utils.exception.InvalidModuleConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ModuleBuilderTest {

    private File file;
    private File configFile;

    @BeforeEach
    public final void init() throws URISyntaxException {
        file = new File(Objects.requireNonNull(ModuleBuilder.class.getClassLoader().getResource("modules/hello-JARJARBINKS-1.0.0.jar")).toURI());
        configFile = new File(Objects.requireNonNull(ModuleBuilder.class.getClassLoader().getResource("module.yml")).toURI());
    }

    @Test
    public final void buildTest() {
        HTModule module = ModuleBuilder.build(file);
        assertTrue(file.exists());
        assertNotNull(module);
        assertEquals("Hello", module.getName());
        assertEquals("1.0.0", module.getVersion());
        assertEquals(List.of("Owl-e"), module.getAuthors());
        assertEquals(new ArrayList<>(), module.getDependencies());
        assertEquals(new ArrayList<>(), module.getSoftDependencies());
        assertEquals("fr.owle.hello.HelloModule", module.getMain());
    }

    @Test
    public final void readYmlTest() throws IOException, InvalidModuleConfigurationException {
        HTModuleConfig config = ModuleBuilder.readYml(configFile);
        assertTrue(configFile.exists());
        assertNotNull(config);
        assertEquals("Hello", config.getName());
        assertEquals("1.0.0", config.getVersion());
        assertEquals(List.of("Owl-e"), config.getAuthors());
        assertEquals(new ArrayList<>(), config.getDependencies());
        assertEquals(new ArrayList<>(), config.getSoftDependencies());
        assertEquals("fr.owle.hello.HelloModule", config.getMain());
    }

    @Test
    public final void buildFailTest() {
        assertNull(ModuleBuilder.build(new File("./")));
    }
}
