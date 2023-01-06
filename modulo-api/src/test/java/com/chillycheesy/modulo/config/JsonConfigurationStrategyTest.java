package com.chillycheesy.modulo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonConfigurationStrategyTest {

    private JsonConfigurationStrategy  jsonConfigurationStrategy;
    private Configuration configuration;

    @BeforeEach
    public void setUp() {
        final InputStream inputStream = MockStream.fromString("""
                {
                    "server": {
                        "name": "My Server",
                        "port": 8080,
                        "enabled": true
                    },
                    "ewoks" : [
                        {
                            "name": "Wicket",
                            "age": 10
                        },
                        {
                            "name": "Graak",
                            "age": 200
                        }
                    ],
                    "assets": [
                        "assets/asset1.png",
                        "assets/asset2.png"
                    ],
                    "price": 10.5
                }
        """);
        jsonConfigurationStrategy = new JsonConfigurationStrategy();
        try { configuration = jsonConfigurationStrategy.loadConfiguration(inputStream); }
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
    public void testLoadedConfigurationEwoks() {
        final List<Configuration> ewoks = configuration.getList("ewoks");
        assertEquals(ewoks.size(), 2);
        final Configuration wicket = ewoks.get(0);
        final Configuration graak = ewoks.get(1);
        assertEquals(wicket.getString("name"), "Wicket");
        assertEquals(wicket.getInteger("age"), 10);
        assertEquals(graak.getString("name"), "Graak");
        assertEquals(graak.getInteger("age"), 200);
    }

    @Test
    public void testLoadedConfigurationAssets() {
        final List<String> assets = configuration.getList("assets");
        assertEquals(assets.size(), 2);
        assertEquals(assets.get(0), "assets/asset1.png");
        assertEquals(assets.get(1), "assets/asset2.png");
    }

    @Test
    public void testLoadedConfigurationPrice() {
        final double price = configuration.getDouble("price");
        assertEquals(price, 10.5);
    }

    @Test
    public void testSaveConfigurationToJson() throws IOException {
        final MockStream.MockOutputStream outputStream = new MockStream.MockOutputStream();
        jsonConfigurationStrategy.saveConfiguration(configuration, outputStream);
        final String result = outputStream.toString();
        assertEquals( "{\"ewoks\":[{\"name\":\"Wicket\",\"age\":10},{\"name\":\"Graak\",\"age\":200}],\"assets\":[\"assets/asset1.png\",\"assets/asset2.png\"],\"server\":{\"name\":\"My Server\",\"enabled\":true,\"port\":8080},\"price\":10.5}", result);
    }
}
