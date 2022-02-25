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
        page.setPath(annotation.path());
        page.setRequestType(annotation.type());
        return page;
    }

    @Override
    public Object createArgument(PageBuilderMetaInfo info, Path path) {
        final Page page = info.getPage();
        final String fullPath = page.getFullPath();
        final Pattern patternFindVar = Pattern.compile(path.value().equals("") ? info.getTypeVariable().getName() : path.value());
        final Matcher matcherFindVar = patternFindVar.matcher(fullPath);
        if (matcherFindVar.find()) {
            final String request = info.getRequest().getRequestURI();
            final Pattern patternFindReq = Pattern.compile("\\{\\w+}");
            final Matcher matcherFindReq = patternFindReq.matcher(request);
            if (matcherFindReq.find(fullPath.indexOf(matcherFindVar.group())))
                return matcherFindReq.group();
        }
        return null;
    }


}
