package com.chillycheesy.modulo.controllers.factory;

import com.chillycheesy.modulo.controllers.ControllerBuilder;
import com.chillycheesy.modulo.controllers.JsonParserController;
import com.chillycheesy.modulo.modules.Module;

import java.lang.annotation.Annotation;

public class JsonResponseControllerAnnotationBinder implements ControllerAnnotationBinder {

    @Override
    public void bindControllerAnnotations(Annotation annotation, Module module, ControllerBuilder builder) {
        if (annotation instanceof JsonResponse) {
            builder.add(new JsonParserController());
        }
    }

    public boolean match(Annotation annotation) {
        return annotation instanceof JsonResponse;
    }
}
