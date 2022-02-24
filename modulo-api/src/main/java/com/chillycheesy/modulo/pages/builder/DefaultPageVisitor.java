package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.Page;

import java.lang.reflect.InvocationTargetException;

public class DefaultPageVisitor implements PageVisitor {

    @Override
    public Page create(PageBuilderMetaInfo info, PageType annotation) {
        final Page page = info.getPage();
        final Class<? extends Page> pageClass = annotation.value();
        try { return pageClass.getConstructor(Page.class).newInstance(page); }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) { ModuloAPI.getLogger().error(null, e); }
        return page;
    }

    @Override
    public Page create(PageBuilderMetaInfo info, HttpRequest annotation) {
        final Page page = info.getPage();

        return page;
    }

    @Override
    public Object createArgument(PageBuilderMetaInfo info, Path path) {
        return null;
    }


}
