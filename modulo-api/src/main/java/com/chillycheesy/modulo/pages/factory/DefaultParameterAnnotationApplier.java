package com.chillycheesy.modulo.pages.factory;

public class DefaultParameterAnnotationApplier implements ParameterAnnotationApplier {

    @Override
    public ParameterBuilder applyHttpParamAnnotation(HttpParam httpParam, ParameterBuilder builder) {
        return builder;
    }

}
