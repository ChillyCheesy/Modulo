package com.chillycheesy.modulo.controllers;

import com.chillycheesy.modulo.config.Configuration;
import com.chillycheesy.modulo.controllers.annotations.HttpParamMethodControllerParameterAnnotationApplier;
import com.chillycheesy.modulo.controllers.annotations.MethodControllerParameterAnnotationApplier;
import com.chillycheesy.modulo.controllers.annotations.PathVariableMethodControllerParameterAnnotationApplier;
import com.chillycheesy.modulo.controllers.annotations.RequestBodyMethodControllerParameterAnnotationApplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Controller that apply that invoke the method of an instance.
 */
public class MethodController implements Controller {

    /**
     * The instance of the class that contains the method
     */
    private final Object instance;

    /**
     * The method to invoke
     */
    private final Method method;

    /**
     * The list of appliers to apply to the method parameters.
     */
    private final List<MethodControllerParameterAnnotationApplier> appliers;

    /**
     * The next controller
     */
    private Controller controller;

    /**
     * Constructor
     * @param instance The instance of the class that contains the method
     * @param method The method to invoke
     * @param appliers The list of appliers to apply to the method parameters.
     */
    public MethodController(Object instance, Method method, List<MethodControllerParameterAnnotationApplier> appliers) {
        this.instance = instance;
        this.method = method;
        this.appliers = appliers;
    }

    /**
     * Constructor.
     * define the default {@link MethodControllerParameterAnnotationApplier} to apply to the method parameters.
     * it includes:
     * <ul>
     *     <li>{@link HttpParamMethodControllerParameterAnnotationApplier}</li>
     *     <li>{@link PathVariableMethodControllerParameterAnnotationApplier}</li>
     *     <li>{@link RequestBodyMethodControllerParameterAnnotationApplier}</li>
     * </ul>
     * @param instance The instance of the class that contains the method
     * @param method The method to invoke
     */
    public MethodController(Object instance, Method method) {
        this(instance, method, new ArrayList<>(Arrays.asList(
                new HttpParamMethodControllerParameterAnnotationApplier(),
                new PathVariableMethodControllerParameterAnnotationApplier(),
                new RequestBodyMethodControllerParameterAnnotationApplier()
        )));
    }


    /**
     * Invoke the method and return the next controller.
     * @param request the http request.
     * @param response the http response.
     * @param configuration the configuration.
     * @return the next controller.
     * @throws Exception if an error occurs.
     */
    @Override
    public Object apply(HttpServletRequest request, HttpServletResponse response, Configuration configuration) throws Exception {
        final Parameter[] parameters = method.getParameters();
        final Object[] args = new Object[parameters.length];
        for (int i = 0 ; i < parameters.length ; i++) {
            final Parameter parameter = parameters[i];
            final Annotation[] annotations = parameter.getAnnotations();
            args[i] = buildArgument(request, response, configuration, parameter, annotations);
        }
        final Object result = method.invoke(instance, args);
        return buildResult(result, method.getReturnType(), configuration);
    }

    private Object buildResult(Object result, Class<?> returnType, Configuration configuration) {
        if (returnType.equals(Void.TYPE)) {
            configuration.set("skip-register-result-in-response", true);
            return true;
        }
        return result;
    }

    private Object buildArgument(HttpServletRequest request, HttpServletResponse response, Configuration configuration, Parameter parameter, Annotation[] annotations) throws IOException {
        final Class<?> clazz = parameter.getType();
        if (annotations.length > 0) {
            Object object = null;
            for (Annotation annotation : annotations)
                for (MethodControllerParameterAnnotationApplier applier : appliers)
                    if (applier.match(annotation))
                        object = applier.apply(annotation, request, response, configuration, instance, method, parameter, object);
            return object;
        } else if (clazz == HttpServletRequest.class) {
            return request;
        } else if (clazz == HttpServletResponse.class) {
            return response;
        } else if (clazz == Configuration.class) {
            return configuration;
        } else if (clazz == Controller.class) {
            return controller;
        } else if (clazz == String.class) {
            return readBody(request);
        }
        return null;
    }

    private String readBody(HttpServletRequest request) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        try (final BufferedReader reader = request.getReader()) {
            reader.lines().forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }

    /**
     * Sets the next controller
     * @param controller The next controller.
     */
    @Override
    public void setNextStep(Controller controller) {
        this.controller = controller;
    }
}
