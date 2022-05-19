package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.utils.Function2;

import java.lang.annotation.Annotation;

public enum PageAnnotations {

    PAGE_TYPE(PageType.class, (info, annotation) -> info.getVisitor().create(info, (PageType) annotation)),
    HTTP_REQUEST(HttpRequest.class, (info, annotation) -> info.getVisitor().create(info, (HttpRequest) annotation));

    private final Class<? extends Annotation> annotationClass;
    private final Function2<PageBuilderMetaInfo, Annotation, Page> applyVisitor;

    PageAnnotations(Class<? extends Annotation> annotationClass, Function2<PageBuilderMetaInfo, Annotation, Page> applyVisitor) {
        this.annotationClass = annotationClass;
        this.applyVisitor = applyVisitor;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Page visit(PageBuilderMetaInfo info, Annotation annotation) {
        return applyVisitor.apply(info, annotation);
    }
}
