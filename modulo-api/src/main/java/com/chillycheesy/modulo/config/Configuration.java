package com.chillycheesy.modulo.config;

import java.util.*;

public class Configuration {

    protected Map<String, Object> properties;
    protected Map<String, Object> defaultProperties;

    public Configuration(Map<String, Object> properties, Map<String, Object> defaultProperties) {
        this.properties = properties;
        this.defaultProperties = defaultProperties;
    }

    public Configuration() { this(new HashMap<>(), new HashMap<>()); }

    public Object get(String key) {
        final Object value = properties.get(key);
        return Objects.nonNull(value) ? value : getDefault(key);
    }

    public String getString(String key) {
        return hasString(key) ? (String) get(key) : "";
    }

    public Integer getInteger(String key) {
        final Object value = get(key);
        return hasInteger(key) ? Integer.parseInt(value.toString()) : 0;
    }

    public Double getDouble(String key) {
        final Object value = get(key);
        return hasDouble(key) ? Double.parseDouble(value.toString()) : 0.0;
    }

    public Boolean getBoolean(String key) {
        final Object value = get(key);
        return hasBoolean(key) && Boolean.parseBoolean(value.toString());
    }

    public <T> List<T> getList(String key) {
        final Object value = get(key);
        return hasList(key) ? (List<T>) value : new ArrayList<>();
    }

    public Object getDefault(String key) {
        return defaultProperties.get(key);
    }

    public String getDefaultString(String key) {
        return hasDefaultString(key) ? (String) getDefault(key) : "";
    }

    public Integer getDefaultInteger(String key) {
        final Object value = getDefault(key);
        return hasDefaultInteger(key) ? Integer.parseInt(value.toString()) : 0;
    }

    public Double getDefaultDouble(String key) {
        final Object value = getDefault(key);
        return hasDefaultDouble(key) ? Double.parseDouble(value.toString()) : 0.0;
    }

    public Boolean getDefaultBoolean(String key) {
        final Object value = getDefault(key);
        return hasDefaultBoolean(key) && Boolean.parseBoolean(value.toString());
    }

    public void set(String key, Object value) {
        properties.put(key, value);
    }

    public void setDefault(String key, Object value) {
        defaultProperties.put(key, value);
    }

    public void remove(String key) {
        properties.remove(key);
    }

    public void removeDefault(String key) {
        defaultProperties.remove(key);
    }

    public boolean has(String key) {
        return properties.containsKey(key);
    }

    public boolean hasList(String key) {
        return has(key) && get(key) instanceof Collection<?>;
    }

    public boolean hasString(String key) {
        return has(key) && get(key) instanceof String;
    }

    public boolean hasInteger(String key) {
        final Object value = get(key);
        if (Objects.nonNull(value)) {
            try { Integer.parseInt(value.toString()); }
            catch (NumberFormatException e) { return false; }
            return true;
        }
        return false;
    }

    public boolean hasDouble(String key) {
        final Object value = get(key);
        if (Objects.nonNull(value)) {
            try { Double.parseDouble(value.toString()); }
            catch (NumberFormatException e) { return false; }
            return true;
        }
        return false;
    }

    public boolean hasBoolean(String key) {
        final Object value = get(key);
        return Objects.nonNull(value) && (value.toString().equals("true") || value.toString().equals("false"));
    }

    public boolean hasDefault(String key) {
        return defaultProperties.containsKey(key);
    }

    public boolean hasDefaultString(String key) {
        return hasDefault(key) && getDefault(key) instanceof String;
    }

    public boolean hasDefaultInteger(String key) {
        final Object value = getDefault(key);
        if (Objects.nonNull(value)) {
            try { Integer.parseInt(value.toString()); }
            catch (NumberFormatException e) { return false; }
            return true;
        }
        return false;
    }

    public boolean hasDefaultDouble(String key) {
        final Object value = getDefault(key);
        if (Objects.nonNull(value)) {
            try { Double.parseDouble(value.toString()); }
            catch (NumberFormatException e) { return false; }
            return true;
        }
        return false;
    }

    public boolean hasDefaultBoolean(String key) {
        final Object value = getDefault(key);
        return Objects.nonNull(value) && (value.toString().equals("true") || value.toString().equals("false"));
    }

    public boolean hasDefaultList(String key) {
        return hasDefault(key) && getDefault(key) instanceof List;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public Map<String, Object> getDefaultProperties() {
        return defaultProperties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setDefaultProperties(Map<String, Object> defaultProperties) {
        this.defaultProperties = defaultProperties;
    }

    public void clear() {
        properties.clear();
    }

    public void clearDefaults() {
        defaultProperties.clear();
    }

    public void clearAll() {
        clear();
        clearDefaults();
    }

    public void merge(Configuration configuration) {
        mergeProperties(configuration);
        mergeDefaults(configuration);
    }

    public void mergeDefaults(Configuration configuration) {
        defaultProperties.putAll(configuration.getDefaultProperties());
    }

    public void mergeProperties(Configuration configuration) {
        properties.putAll(configuration.getProperties());
    }

    public Map<String, Object> getMergedProperties() {
        final Map<String, Object> mergedProperties = new HashMap<>();
        mergedProperties.putAll(defaultProperties);
        mergedProperties.putAll(properties);
        return mergedProperties;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "properties=" + properties +
                ", defaultProperties=" + defaultProperties +
                '}';
    }
}
