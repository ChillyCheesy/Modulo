package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        page.setRequestType(annotation.type());
        page.setPath(annotation.path());
        return page;
    }

    @Override
    public Object createArgument(PageBuilderMetaInfo info, Path path) {
        final Page page = info.getPage();
        final String requestPath = info.getRequest().getRequestURI();
        final Pattern patternFindStart = Pattern.compile("\\{" + path.value() + "}");
        final Matcher matcherFindStart = patternFindStart.matcher(page.getFullPath());
        final int start = matcherFindStart.start();
        final Pattern pattern = Pattern.compile("(\\w+$|\\w+(?!/))");
        return null;
    }


}
