package com.chillycheesy.modulo.controllers.factory;

import com.chillycheesy.modulo.controllers.ControllerBuilder;
import com.chillycheesy.modulo.controllers.HttpMethodController;
import com.chillycheesy.modulo.controllers.HttpPathVariableController;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.InvalidPathVariableException;

import java.lang.annotation.Annotation;

public class RequestControllerAnnotationBinder implements ControllerAnnotationBinder {

    @Override
    public void bindControllerAnnotations(Annotation annotation, Module module, ControllerBuilder builder) throws InvalidPathVariableException {
        if (annotation instanceof final Request request) {
            final HttpMethodController methodController = new HttpMethodController(request.method());
            final HttpPathVariableController pathVariableController = new HttpPathVariableController(request.path());
            builder.add(methodController).add(pathVariableController);
        }
    }

    public boolean match(Annotation annotation) {
        return annotation instanceof Request;
    }

}
