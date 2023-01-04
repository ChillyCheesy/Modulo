package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ParameterAnnotationApplier {

    void applyHttpParamAnnotation(HttpParam httpParam, Page page, HttpServletRequest request, HttpServletResponse response, ParameterBuilder builder);

}
