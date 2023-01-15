package com.chillycheesy.modulo.config;

import com.chillycheesy.modulo.modules.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FireConfigurationFactoryTest {

    private static class SimpleLoader implements ConfigurationLoaderStrategy {

        @Override
        public Configuration loadConfiguration(InputStream inputStream) throws IOException {
            final StringBuilder builder = new StringBuilder();
            try (final InputStreamReader reader = new InputStreamReader(inputStream)) {
                int c;
                while ((c = reader.read()) != -1) {
                    builder.append((char) c);
                }
            }
            final Configuration configuration = new Configuration();
            configuration.set("test", builder.toString());
            return configuration;
        }

        @Override
        public void saveConfiguration(Configuration configuration, OutputStream outputStream) throws IOException {

        }

    }

    private Module module;

    @BeforeEach
    private void init() throws IOException {
        module = mock(Module.class);
        final JarFile jarFile = mock(JarFile.class);
        final JarEntry entry = mock(JarEntry.class);
        final InputStream inputStream = MockStream.fromString("Wicket is my best friend");
        when(module.getJarFile()).thenReturn(jarFile);
        when(module.getName()).thenReturn("EndorModule");
        when(jarFile.getJarEntry("test.txt")).thenReturn(entry);
        when(jarFile.getInputStream(entry)).thenReturn(inputStream);
    }

    @Test
    public void shouldCreateConfiguration() {
        final FileConfigurationFactory factory = new FileConfigurationFactory(module, "test.txt");
        final File file = new File("configs/EndorModule/test.txt");
        assertFalse(file.exists());
        final Configuration configuration = factory.createConfiguration(new SimpleLoader());
        assertEquals("Wicket is my best friend", configuration.getString("test"));
        assertTrue(file.exists());
    }

    @AfterEach
    private void clean() {
        final File file = new File("configs/EndorModule/test.txt");
        if (file.exists()) {
            file.delete();
        }
    }

}
