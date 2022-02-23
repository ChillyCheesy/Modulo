package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class DefaultPageVisitor implements PageVisitor {

    @Override
    public Page create(Page page, PageType annotation) {
        final Class<? extends Page> pageClass = annotation.value();
        try {
            return pageClass.getConstructor(Page.class).newInstance(page);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            ModuloAPI.getLogger().error(null, e);
        }
        return page;
    }

    @Override
    public Page create(Page page, HttpRequest annotation) {
        page.setRequestType(annotation.type());
        page.setPath(annotation.path());
        return page;
    }

    @Override
    public Object createArgument(Object object, HttpServletRequest request, HttpServletResponse response, Path path) {
        return null;
    }


}
