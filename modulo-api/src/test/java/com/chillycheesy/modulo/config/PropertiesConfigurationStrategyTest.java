package com.chillycheesy.modulo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertiesConfigurationStrategyTest {

    private PropertiesConfigurationStrategy propertiesConfigurationStrategy;
    private Configuration configuration;

    @BeforeEach
    public void setUp() {
        final InputStream inputStream = MockStream.fromString("""
                server.name = My Server
                server.port = 8080
                server.enabled = true
                price = 10.5
        """);
        propertiesConfigurationStrategy = new PropertiesConfigurationStrategy();
        try { configuration = propertiesConfigurationStrategy.loadConfiguration(inputStream); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @Test
    public void testLoadedConfigurationServer() {
        final String name = configuration.getString("server.name");
        final int port = configuration.getInteger("server.port");
        final boolean enabled = configuration.getBoolean("server.enabled");
        assertEquals(name, "My Server");
        assertEquals(port, 8080);
        assertTrue(enabled);
    }

    @Test
    public void testLoadedConfigurationPrice() {
        final double price = configuration.getDouble("price");
        assertEquals(price, 10.5);
    }

    @Test
    public void testSaveConfigurationToProperties() throws IOException {
        final MockStream.MockOutputStream outputStream = new MockStream.MockOutputStream();
        propertiesConfigurationStrategy.saveConfiguration(configuration, outputStream);
        final InputStream inputStream = MockStream.fromString(outputStream.toString());
        final Configuration newConfiguration = propertiesConfigurationStrategy.loadConfiguration(inputStream);
        assertEquals(configuration, newConfiguration);
    }

}
