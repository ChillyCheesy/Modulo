package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public interface PageVisitor {
    Page create(Page page, PageType annotation);
    Page create(Page page, HttpRequest annotation);
    Object createArgument(Object object, HttpServletRequest request, HttpServletResponse response, Path path);
}
