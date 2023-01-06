package com.chillycheesy.modulo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTest {

    private Configuration configuration;

    @BeforeEach
    public void init() { configuration = new Configuration(); }

    @Test
    public void testGet() {
        assertNull(configuration.get("key"));
        configuration.set("key", "value");
        assertEquals("value", configuration.get("key"));
    }

    @Test
    public void testGetString() {
        assertEquals("", configuration.getString("key"));
        configuration.set("key", "value");
        assertEquals("value", configuration.getString("key"));
    }

    @Test
    public void testGetInteger() {
        assertEquals(0, configuration.getInteger("key"));
        configuration.set("key", 1);
        assertEquals(1, configuration.getInteger("key"));
    }

    @Test
    public void testGetDouble() {
        assertEquals(0.0, configuration.getDouble("key"));
        configuration.set("key", 1.0);
        assertEquals(1.0, configuration.getDouble("key"));
    }

    @Test
    public void testGetBoolean() {
        assertEquals(false, configuration.getBoolean("key"));
        configuration.set("key", true);
        assertTrue(configuration.getBoolean("key"));
    }

    @Test
    public void testGetList() {
        assertEquals(0, configuration.getList("key").size());
        configuration.set("key", new ArrayList<>());
        assertEquals(0, configuration.getList("key").size());
        configuration.set("key", Arrays.asList("a", "b", "c"));
        assertEquals(3, configuration.getList("key").size());
    }

    @Test
    public void testGetWithDefaultValue() {
        assertEquals("wicket the Ewok", configuration.getString("ewok", "wicket the Ewok"));
        configuration.set("ewok", "chewbacca");
        assertEquals("chewbacca", configuration.getString("ewok", "wicket the Ewok"));
    }

    @Test
    public void testGetStringWithDefaultValue() {
        assertEquals("wicket the Ewok", configuration.getString("ewok", "wicket the Ewok"));
        configuration.set("ewok", "chewbacca");
        assertEquals("chewbacca", configuration.getString("ewok", "wicket the Ewok"));
    }

    @Test
    public void testGetIntegerWithDefaultValue() {
        assertEquals(1, configuration.getInteger("ewok", 1));
        configuration.set("ewok", 2);
        assertEquals(2, configuration.getInteger("ewok", 1));
    }

    @Test
    public void testGetDoubleWithDefaultValue() {
        assertEquals(1.0, configuration.getDouble("ewok", 1.0));
        configuration.set("ewok", 2.0);
        assertEquals(2.0, configuration.getDouble("ewok", 1.0));
    }

    @Test
    public void testGetBooleanWithDefaultValue() {
        assertTrue(configuration.getBoolean("ewok", true));
        configuration.set("ewok", false);
        assertFalse(configuration.getBoolean("ewok", true));
    }

    @Test
    public void testGetListWithDefaultValue() {
        assertEquals(0, configuration.getList("ewok", new ArrayList<>()).size());
        configuration.set("ewok", Arrays.asList("a", "b", "c"));
        assertEquals(3, configuration.getList("ewok", new ArrayList<>()).size());
    }


    @Test
    public void testSet() {
        configuration.set("key", "value");
        assertEquals("value", configuration.get("key"));
        configuration.set("key", "newValue");
        assertEquals("newValue", configuration.get("key"));
    }

    @Test
    public void testGetDefaultValue() {
        configuration.setDefault("key", "value");
        assertEquals("value", configuration.getDefault("key"));
        assertEquals("value", configuration.get("key"));
        configuration.set("key", "newValue");
        assertEquals("newValue", configuration.get("key"));
    }

    @Test
    public void testGetDefaultStringValue() {
        configuration.setDefault("key", "value");
        assertEquals("value", configuration.getDefaultString("key"));
        assertEquals("value", configuration.getString("key"));
        configuration.set("key", "newValue");
        assertEquals("newValue", configuration.getString("key"));
    }

    @Test
    public void testGetDefaultIntegerValue() {
        configuration.setDefault("key", 1);
        assertEquals(1, configuration.getDefaultInteger("key"));
        assertEquals(1, configuration.getInteger("key"));
        configuration.set("key", 2);
        assertEquals(2, configuration.getInteger("key"));
    }

    @Test
    public void testGetDefaultDoubleValue() {
        configuration.setDefault("key", 1.0);
        assertEquals(1.0, configuration.getDefaultDouble("key"));
        assertEquals(1.0, configuration.getDouble("key"));
        configuration.set("key", 2.0);
        assertEquals(2.0, configuration.getDouble("key"));
    }

    @Test
    public void testGetDefaultBooleanValue() {
        configuration.setDefault("key", true);
        assertTrue(configuration.getDefaultBoolean("key"));
        assertTrue(configuration.getBoolean("key"));
        configuration.set("key", false);
        assertFalse(configuration.getBoolean("key"));
    }

    @Test
    public void testGetDefaultListValue() {
        configuration.setDefault("key", Arrays.asList("a", "b", "c"));
        assertEquals(3, configuration.getDefaultList("key", new ArrayList<>()).size());
        assertEquals(3, configuration.getList("key").size());
        configuration.set("key", Arrays.asList("d", "e", "f", "g"));
        assertEquals(4, configuration.getList("key").size());
    }

    @Test
    public void testClear() {
        configuration.set("key", "value");
        assertEquals("value", configuration.get("key"));
        configuration.clear();
        assertNull(configuration.get("key"));
    }

    @Test
    public void testClearDefault() {
        configuration.setDefault("key", "value");
        assertEquals("value", configuration.getDefault("key"));
        configuration.clearDefaults();
        assertNull(configuration.getDefault("key"));
    }

    @Test
    public void testClearAll() {
        configuration.set("key", "value");
        assertEquals("value", configuration.get("key"));
        configuration.setDefault("key", "value2");
        configuration.clearAll();
        assertNull(configuration.getDefault("key"));
        assertNull(configuration.get("key"));
    }

}
