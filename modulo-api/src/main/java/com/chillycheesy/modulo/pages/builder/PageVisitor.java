package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import java.lang.reflect.InvocationTargetException;

public interface PageVisitor {
    Page create(Page page, PageType annotation);
    Page create(Page page, HttpRequest annotation);
}
