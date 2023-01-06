package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ResponseAnnotationApplier {

    boolean applyResponseBodyAnnotation(ResponseBody responseBody, HttpServletResponse response, Page page, Object result) throws IOException;

    boolean applyResponseResourceAnnotation(ResponseResource responseResource, Page page, HttpServletRequest request, HttpServletResponse response, Object result) throws IOException;

}
