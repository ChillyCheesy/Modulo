package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import java.lang.reflect.InvocationTargetException;

public class DefaultPageVisitor implements PageVisitor {

    @Override
    public Page create(Page page, PageType annotation) {
        final Class<? extends Page> pageClass = annotation.value();
        try {
            return pageClass.getConstructor(Page.class).newInstance(page);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public Page create(Page page, HttpRequest annotation) {
        page.setRequestType(annotation.type());
        page.setPath(annotation.path());
        return page;
    }

}
