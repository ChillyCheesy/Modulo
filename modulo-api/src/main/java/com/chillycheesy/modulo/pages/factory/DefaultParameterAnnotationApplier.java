package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;

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

}
