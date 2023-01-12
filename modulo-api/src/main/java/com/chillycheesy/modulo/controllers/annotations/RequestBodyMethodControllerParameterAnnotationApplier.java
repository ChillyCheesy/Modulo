package com.chillycheesy.modulo.controllers.annotations;

import com.chillycheesy.modulo.config.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Apply the {@link RequestBody} decorator.
 *
 * @author chillycheesy
 */
public class RequestBodyMethodControllerParameterAnnotationApplier implements MethodControllerParameterAnnotationApplier {

    /**
     * Apply the annotation to the parameter and return the argument to pass to the method.
     * @param annotation The annotation to apply.
     * @param request The http request.
     * @param response The httpResponse.
     * @param configuration The current configuration.
     * @param instance The method instance.
     * @param method The method of the parameter.
     * @param parameter The method parameter.
     * @param currentArgument The current returned argument value.
     *
     * @return The argument to pass to the method.
     */
    @Override
    public Object apply(Annotation annotation, HttpServletRequest request, HttpServletResponse response, Configuration configuration, Object instance, Method method, Parameter parameter, Object currentArgument) {
        try (final BufferedReader reader = request.getReader()) {
            final String content = contentAsString(reader);
            if (parameter.getType().equals(String.class)) return content;
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content, parameter.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String contentAsString(BufferedReader reader) {
        final StringBuilder builder = new StringBuilder();
        reader.lines().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Check if the annotation is supported by this applier.
     * @param annotation The annotation to apply.
     * @return True if the annotation is supported by this applier.
     */
    @Override
    public boolean match(Annotation annotation) {
        return annotation instanceof RequestBody;
    }

}
