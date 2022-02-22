package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class PageBuilder {

    private final PageVisitor visitor;

    private PageBuilder(PageVisitor visitor) {
        this.visitor = visitor;
    }

    private Page createPageFromObject(Object object) {
        final Page page = createPageFromClassAnnotations(object);
        return createPageFromMethodsAnnotations(page, object.getClass().getMethods());
    }

    private Page createPageFromClassAnnotations(Object object) {
        return new Page("");
    }

    private Page createPageFromMethodsAnnotations(Page page, Method[] methods) {
        for (Method method : methods) {
            for (PageAnnotations pageAnnotations : PageAnnotations.values()) {
                final Annotation annotation = method.getAnnotation(pageAnnotations.getAnnotationClass());
                if (annotation != null) {
                    page = pageAnnotations.visit(page, annotation, visitor);
                }
            }
        }
        return page;
    }

    public static Page build(Object object, PageVisitor visitor) {
        final PageBuilder builder = new PageBuilder(visitor);
        return builder.createPageFromObject(object);
    }

    public static Page build(Object object) {
        return build(object, new DefaultPageVisitor());
    }
}
