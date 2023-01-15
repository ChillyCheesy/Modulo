package com.chillycheesy.modulo.controllers.factory;

import com.chillycheesy.modulo.controllers.ControllerBuilder;
import com.chillycheesy.modulo.modules.Module;

import java.lang.annotation.Annotation;

public interface ControllerAnnotationBinder {
    void bindControllerAnnotations(Annotation annotation, Module module, ControllerBuilder builder) throws Exception;

    boolean match(Annotation annotation);
}
