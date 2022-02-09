package com.chillycheesy.modulo.pages;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * The class that is used to parse all the request made by the client
 *
 * @author henouille
 */
public class ParameterParser {

    private final Parameter parameter;
    private final String body;
    private final String param;
    private final String methodPath;
    private final String path;

    private final Annotation[] annotations;

    private ParameterParser(Parameter parameter, Annotation[] annotations, String methodPath, String path, String param, String body) {
        this.parameter = parameter;
        this.body = body;
        this.path = path;
        this.methodPath = methodPath;
        this.param = param;
        this.annotations = annotations;
    }

    /**
     *
     * @param parameter the parameter you want to use to parse the request
     * @param methodPath the method path you want to call
     * @param path the path you want to call
     * @param param the parameter of the path
     * @param body the body of the request
     * @return an {@link Object} depending on what the method you call returns
     * @throws JsonProcessingException
     */
    public static Object parse(Parameter parameter, String methodPath, String path, String param, String body) throws JsonProcessingException {
        final ParameterParser parser = new ParameterParser(parameter, parameter.getAnnotations(), methodPath, path, param, body);
        return parser.parse();
    }

    private Object parse() throws JsonProcessingException {
        final Class<?> c = parameter.getType();
        final ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        String value = "";
        if (annotations.length > 0) {
            final Annotation annotation = annotations[0];
            if (annotation instanceof Body) {
                value = body;
            } else if (annotation instanceof QueryParam) {
                final String param = ((QueryParam) annotation).value();
                final String defaultValue = ((QueryParam) annotation).defaultValue();
                value = parseParameters(param, defaultValue);
            } else if (annotation instanceof PathParam) {
                final String name = ((PathParam) annotation).value();
                value = parsePathParam(name);
            }
        }
        return c.equals(String.class) ? value : mapper.readValue(value, c);
    }

    private String parsePathParam(String name) {
        name = name.equals("") ? parameter.getName() : name;
        final String[] pathParts = path.split("/");
        final String[] methodPathParts = methodPath.split("/");
        final int size = Math.min(pathParts.length, methodPathParts.length);
        for (int i = 0; i < size; i++) {
            if (methodPathParts[i].equals("{" + name + "}")) {
                return pathParts[i];
            }
        }
        return "null";
    }

    private String parseParameters(String parameterName, String defaultValue) {
        parameterName = parameterName.equals("") ? parameter.getName() : parameterName;
        if (param != null && param.length() > 0) {
            final String[] params = param.split("&");
            for (String param : params) {
                final String key = param.split("=")[0];
                final String value = param.split("=").length == 1 || param.split("=")[1].equals("") ? defaultValue : param.split("=")[1];
                if (parameterName.equals(key)) {
                    return value;
                }
            }
        }
        return defaultValue;
    }

}
