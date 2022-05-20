package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.natif.PathVariablePage;

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
        final HttpRequestType type = annotation.type();
        final String path = annotation.path();
        return buildPageFromPath(type, path);
    }

    @Override
    public Object createArgument(PageBuilderMetaInfo info, Path path) {
        final Page page = info.getPage();
        final String value = path.value();
        if (page instanceof PathVariablePage) {
            final PathVariablePage pathVariablePage = (PathVariablePage) page;
            if (pathVariablePage.getKey().equals(value)) {
                final String requestURI = info.getRequest().getRequestURI();
                final Pattern pattern = Pattern.compile("\\w+$");
                final Matcher matcher = pattern.matcher(requestURI);
                return matcher.find() ? matcher.group() : null;
            }
        }
        final Page parent = page.getParent();
        if (parent != null) {
            info.setPage(page.getParent());
            return createArgument(info, path);
        }
        return null;
    }

    private Page buildPageFromPath(HttpRequestType type, String path) {
        final String[] sections = path.replaceAll("^/|/$", "").split("/");
        final Page page = buildPageFromSection(type, sections[0]);
        if (sections.length > 1) {
            final String newPath = path.substring(sections[0].length());
            final Page subpages = buildPageFromPath(type, newPath);
            page.addSubPage(subpages);
        }
        return page;
    }

    private Page buildPageFromSection(HttpRequestType type, String section) {
        final Pattern pattern = Pattern.compile("^\\{\\w+}$");
        final Matcher matcher = pattern.matcher(section);
        if (matcher.find()) {
            final PathVariablePage page = new PathVariablePage(type, section);
            final String key = matcher.group().replaceAll("^\\{|}$", "");
            page.setKey(key);
            return page;
        }
        return new Page(type, section);
    }


}
