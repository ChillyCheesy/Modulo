package com.chillycheesy.modulo.config;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Configuration {

    protected Map<String, Object> properties;
    protected Map<String, Object> defaultProperties;

    public Configuration(Map<String, Object> properties, Map<String, Object> defaultProperties) {
        this.properties = properties;
        this.defaultProperties = defaultProperties;
    }

    public Configuration(Configuration ...configurations) {
        this();
        for (Configuration configuration : configurations) {
            merge(configuration);
        }
    }

    public Configuration() { this(new HashMap<>(), new HashMap<>()); }

    public Object get(String key) {
        final Object value = properties.get(key);
        return Objects.nonNull(value) ? value : getDefault(key);
    }

    public String getString(String key, String defaultValue) {
        return hasString(key) ? (String) get(key) : defaultValue;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public Integer getInteger(String key, Integer defaultValue) {
        final Object value = get(key);
        return hasInteger(key) ? Integer.parseInt(value.toString()) : defaultValue;
    }

    public Integer getInteger(String key) {
        return getInteger(key, 0);
    }

    public Double getDouble(String key, Double defaultValue) {
        final Object value = get(key);
        return hasDouble(key) ? Double.parseDouble(value.toString()) : defaultValue;
    }

    public Double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    public Boolean getBoolean(String key, Boolean defaultValue) {
        final Object value = get(key);
        return hasBoolean(key) ? Boolean.parseBoolean(value.toString()) : defaultValue;
    }

    public <T> List<T> getList(String key) {
        return getList(key, new ArrayList<>());
    }

    public <T> List<T> getList(String key, List<T> defaultValue) {
        final Object value = get(key);
        return hasList(key) ? (List<T>) value : defaultValue;
    }

    public Object getDefault(String key) {
        return defaultProperties.get(key);
    }

    public String getDefaultString(String key) {
        return getDefaultString(key, "");
    }

    public String getDefaultString(String key, String defaultValue) {
        return hasDefaultString(key) ? (String) getDefault(key) : defaultValue;
    }

    public Integer getDefaultInteger(String key, Integer defaultValue) {
        final Object value = getDefault(key);
        return hasDefaultInteger(key) ? Integer.parseInt(value.toString()) : defaultValue;
    }

    public Integer getDefaultInteger(String key) {
        return getDefaultInteger(key, 0);
    }

    public Double getDefaultDouble(String key, Double defaultValue) {
        final Object value = getDefault(key);
        return hasDefaultDouble(key) ? Double.parseDouble(value.toString()) : defaultValue;
    }

    public Double getDefaultDouble(String key) {
        return getDefaultDouble(key, 0.0);
    }

    public Boolean getDefaultBoolean(String key) {
        return getDefaultBoolean(key, false);
    }

    public Boolean getDefaultBoolean(String key, Boolean defaultValue) {
        final Object value = getDefault(key);
        return hasDefaultBoolean(key) ? Boolean.parseBoolean(value.toString()) : defaultValue;
    }

    public <T> List<T> getDefaultList(String key) {
        return getDefaultList(key, new ArrayList<>());
    }

    public <T> List<T> getDefaultList(String key, List<T> defaultValue) {
        final Object value = getDefault(key);
        return hasDefaultList(key) ? (List<T>) value : defaultValue;
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
        return properties.containsKey(key) || hasDefault(key);
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

    public boolean isModified() {
        return !properties.equals(defaultProperties);
    }

    public void forEach(BiConsumer<String, String> setProperty) {
        forEach("", setProperty);
    }

    public void forEach(String key, BiConsumer<String, String> setProperty) {
        getMergedProperties().entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(key))
            .collect(Collectors.toMap(keyEntry -> keyEntry.getKey().replaceFirst("properties\\.", ""), e -> String.valueOf(e.getValue())))
            .forEach(setProperty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(properties, that.properties) && Objects.equals(defaultProperties, that.defaultProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, defaultProperties);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "properties=" + properties +
                ", defaultProperties=" + defaultProperties +
                '}';
    }


}
