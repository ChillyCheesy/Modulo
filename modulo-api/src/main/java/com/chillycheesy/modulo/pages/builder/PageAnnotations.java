package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.utils.Function3;

import java.lang.annotation.Annotation;

public enum PageAnnotations {

    PAGE_TYPE(PageType.class, (PageVisitor visitor, Page page, Annotation annotation) -> visitor.create(page, (PageType) annotation)),
    HTTP_REQUEST(HttpRequest.class, (PageVisitor visitor, Page page, Annotation annotation) -> visitor.create(page, (PageType) annotation));

    private final Class<? extends Annotation> annotationClass;
    private final Function3<PageVisitor, Page, Annotation, Page> applyVisitor;

    PageAnnotations(Class<? extends Annotation> annotationClass, Function3<PageVisitor, Page, Annotation, Page> applyVisitor) {
        this.annotationClass = annotationClass;
        this.applyVisitor = applyVisitor;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Page visit(Page page, Annotation annotation, PageVisitor visitor) {
        return applyVisitor.apply(visitor, page, annotation);
    }
}
