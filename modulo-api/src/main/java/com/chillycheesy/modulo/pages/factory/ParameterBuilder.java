package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class ParameterBuilder {

    private Parameter parameter;
    private Annotation[] annotations;
    private Object parameterValue;
    private ParameterAnnotationApplier parameterAnnotationApplier;

    public ParameterBuilder setParameter(Parameter parameter) {
        this.parameter = parameter;
        return this;
    }

    public ParameterBuilder setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
        return this;
    }

    public ParameterBuilder setParameterAnnotationApplier(ParameterAnnotationApplier parameterAnnotationApplier) {
        this.parameterAnnotationApplier = parameterAnnotationApplier;
        return this;
    }

    public ParameterBuilder setParameterValue(Object parameterValue) {
        this.parameterValue = parameterValue;
        return this;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public ParameterAnnotationApplier getParameterAnnotationApplier() {
        return parameterAnnotationApplier;
    }

    public Object getParameterValue() {
        return parameterValue;
    }

    public Object build(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        applyDefaultParameter(page, request, response);
        applyAnnotation(page, request, response);
        return parameterValue;
    }

    private void applyDefaultParameter(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (parameter.getType().equals(Page.class)) {
            parameterValue = page;
        } else if (parameter.getType().equals(HttpServletRequest.class)) {
            parameterValue = request;
        } else if (parameter.getType().equals(HttpServletResponse.class)) {
            parameterValue = response;
        } else if (parameter.getType().equals(String.class)) {
            parameterValue = getBody(request);
        }
    }

    private String getBody(HttpServletRequest request) throws IOException {
        try (
            final InputStream inputStream = request.getInputStream();
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ) {
            final byte[] bytes = bufferedInputStream.readAllBytes();
            return new String(bytes);
        }
    }

    private void applyAnnotation(Page page, HttpServletRequest request, HttpServletResponse response) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof HttpParam)
                parameterAnnotationApplier.applyHttpParamAnnotation((HttpParam) annotation, page, request, response, this);
            if (annotation instanceof PathVariable)
                parameterAnnotationApplier.applyPathVariable((PathVariable) annotation, page, request, response, this);
        }
    }
}
