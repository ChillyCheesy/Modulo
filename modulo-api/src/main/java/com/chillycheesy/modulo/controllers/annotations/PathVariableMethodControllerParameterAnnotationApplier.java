package com.chillycheesy.modulo.controllers.annotations;


import com.chillycheesy.modulo.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.chillycheesy.modulo.controllers.HttpPathVariableController.PATH_VARIABLE_SECTION;

/**
 * Apply the {@link PathVariable} decorator.
 *
 * @author chillycheesy
 */
public class PathVariableMethodControllerParameterAnnotationApplier implements MethodControllerParameterAnnotationApplier {

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
        final String annotationValue = ((PathVariable) annotation).value();
        final String key = annotationValue.equals("?") ? parameter.getName() : annotationValue;
        return configuration.getString(String.format("%s.%s", PATH_VARIABLE_SECTION, key), null);
    }

    /**
     * Check if the annotation is supported by this applier.
     *
     * @param annotation The annotation to apply.
     * @return True if the annotation is supported by this applier.
     */
    @Override
    public boolean match(Annotation annotation) {
        return annotation instanceof PathVariable;
    }
}
