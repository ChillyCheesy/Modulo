package com.chillycheesy.modulo.controllers.methodcontroller;

import com.chillycheesy.modulo.config.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Interface for a class that can apply an annotation to a method parameter.
 * It is used to build the argument that will be passed to the method.
 * Check {@link com.chillycheesy.modulo.controllers.MethodController} for more information.
 *
 * @author chillycheesy
 */
public interface MethodControllerParameterAnnotationApplier {

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
    Object apply(Annotation annotation, HttpServletRequest request, HttpServletResponse response, Configuration configuration, Object instance, Method method, Parameter parameter, Object currentArgument);


    /**
     * Check if the annotation is supported by this applier.
     * @param annotation The annotation to apply.
     * @return true if the annotation is supported.
     */
    boolean match(Annotation annotation);
}
