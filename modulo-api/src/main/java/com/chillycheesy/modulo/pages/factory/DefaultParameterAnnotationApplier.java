package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.TemplatePage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class DefaultParameterAnnotationApplier implements ParameterAnnotationApplier {

    @Override
    public void applyHttpParamAnnotation(HttpParam httpParam, Page page, HttpServletRequest request, HttpServletResponse response, ParameterBuilder builder) {
        final Parameter parameter = builder.getParameter();
        final String paramValue = httpParam.value();
        final String key = paramValue.equals("?")
            ? parameter.getName()
            : paramValue;
        final String value = request.getParameter(key);
        builder.setParameterValue(value == null ? httpParam.defaultValue() : value);
    }

    @Override
    public void applyPathVariable(PathVariable pathVariable, Page page, HttpServletRequest request, HttpServletResponse response, ParameterBuilder builder) {
        final Parameter parameter = builder.getParameter();
        final String paramValue = pathVariable.value();
        final String key = paramValue.equals("?")
                ? parameter.getName()
                : paramValue;
        if (page instanceof final TemplatePage templatePage) {
            final String value = templatePage.getTemplateVariable(key);
            builder.setParameterValue(value == null ? pathVariable.defaultValue() : value);
        }
    }


}
