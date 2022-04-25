package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.utils.Function2;
import java.lang.annotation.Annotation;

public enum PageParameterAnnotations {

    PATH(Path.class, (info, annotation) -> info.getVisitor().createArgument(info, (Path) annotation));

    private final Class<? extends Annotation> annotationClass;
    private final Function2<PageBuilderMetaInfo, Annotation, Object> applyVisitor;

    PageParameterAnnotations(Class<? extends Annotation> annotationClass, Function2<PageBuilderMetaInfo, Annotation, Object> applyVisitor) {
        this.annotationClass = annotationClass;
        this.applyVisitor = applyVisitor;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Object visit(PageBuilderMetaInfo info, Annotation annotation) {
        return applyVisitor.apply(info, annotation);
    }

}
