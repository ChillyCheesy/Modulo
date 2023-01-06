package com.chillycheesy.modulo.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JacksonConfigurationStrategy implements ConfigurationLoaderStrategy {

    protected ObjectMapper mapper;

    public JacksonConfigurationStrategy(JsonFactory factory) {
        mapper = new ObjectMapper(factory);
    }

    @Override
    public Configuration loadConfiguration(InputStream inputStream) throws IOException {
        final JsonNode node = mapper.readTree(inputStream);
        final Configuration configuration = new Configuration();
        return loadConfiguration(node, configuration);
    }

    @Override
    public void saveConfiguration(Configuration configuration, OutputStream outputStream) throws IOException {
        final ObjectNode node = buildNode(configuration);
        mapper.writeValue(outputStream, node);
    }

    private ObjectNode buildNode(Configuration configuration) {
        final Map<String, Object> map = configuration.getMergedProperties();
        return buildNode(configuration, map);
    }

    private ObjectNode buildNode(Configuration configuration, Map<String, Object> map) {
        return buildNode(configuration, map, "");
    }

    private ObjectNode buildNode(Configuration configuration, Map<String, Object> map, String prefix) {
        final ObjectNode node = mapper.createObjectNode();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final String key = entry.getKey();
            final String shortedKey = key.replace(prefix, "");
            final int dotIndex = shortedKey.indexOf(".") ;
            final String newKey = shortedKey.substring(0, dotIndex > 0 ? dotIndex : shortedKey.length());
            if (!shortedKey.equals(newKey)) {
                final String newPrefix = prefix + newKey + ".";
                final Map<String, Object> subMap = getObjectMapByPrefix(map, newPrefix);
                final ObjectNode subNode = buildNode(configuration, subMap, newPrefix);
                node.set(newKey, subNode);
            } else if (configuration.hasBoolean(key))
                node.put(newKey, configuration.getBoolean(key));
            else if (configuration.hasInteger(key))
                node.put(newKey, configuration.getInteger(key));
            else if (configuration.hasDouble(key))
                node.put(newKey, configuration.getDouble(key));
            else if (configuration.hasString(key))
                node.put(newKey, configuration.getString(key));
            else if (configuration.hasList(key)) {
                final List<?> list = configuration.getList(key);
                if (list.size() > 0 && list.get(0) instanceof Configuration)
                    node.putArray(newKey).addAll(list.stream()
                        .map(config -> (Configuration) config)
                        .map(this::buildNode).collect(Collectors.toList()));
                else node.set(newKey, mapper.valueToTree(configuration.getList(key)));
            }
        }
        return node;
    }

    private Map<String, Object> getObjectMapByPrefix(Map<String, Object> map, String prefix) {
        return map.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith(prefix))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Configuration loadConfiguration(JsonNode node, Configuration configuration) {
        return loadConfiguration(node, configuration, "");
    }

    private Configuration loadConfiguration(JsonNode node, Configuration configuration, String prefix) {
        final Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            final String fieldName = fieldNames.next();
            final JsonNode childNode = node.get(fieldName);
            final String key = prefix + fieldName;
            if (childNode.isObject()) {
                final Configuration childConfiguration = new Configuration();
                configuration.mergeDefaults(loadConfiguration(childNode, childConfiguration, key + "."));
            } else if (childNode.isArray()) {
                final Iterator<JsonNode> elements = childNode.elements();
                final List<Object> configurationList = new ArrayList<>();
                while (elements.hasNext()) {
                    final JsonNode element = elements.next();
                    if (element.getNodeType().equals(JsonNodeType.OBJECT)) {
                        final Configuration childConfiguration = new Configuration();
                        configurationList.add(loadConfiguration(element, childConfiguration));
                    } else configurationList.add(element.asText());
                }
                configuration.setDefault(key, configurationList);
            } else configuration.setDefault(key, childNode.asText());
        }
        return configuration;
    }

}
