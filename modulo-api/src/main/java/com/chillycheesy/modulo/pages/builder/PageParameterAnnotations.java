package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.utils.Function5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

public enum PageParameterAnnotations {
    PATH(Path.class, (object, request, response, annotation, visitor) -> visitor.createArgument(object, request, response, (Path) annotation));

    private final Class<? extends Annotation> annotationClass;
    private final Function5<Object, HttpServletRequest, HttpServletResponse, Annotation, PageVisitor, Object> applyVisitor;

    PageParameterAnnotations(Class<? extends Annotation> annotationClass, Function5<Object, HttpServletRequest, HttpServletResponse, Annotation, PageVisitor, Object> applyVisitor) {
        this.annotationClass = annotationClass;
        this.applyVisitor = applyVisitor;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Object visit(Object object, HttpServletRequest request, HttpServletResponse response, Annotation annotation, PageVisitor visitor) {
        return applyVisitor.apply(object, request, response, annotation, visitor);
    }

}
