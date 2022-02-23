package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageResponse;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

public class PageBuilder {

    private final PageVisitor visitor;

    private PageBuilder(PageVisitor visitor) {
        this.visitor = visitor;
    }

    public static Page build(Object object, PageVisitor visitor) {
        final PageBuilder builder = new PageBuilder(visitor);
        return builder.createPageFromObject(object);
    }

    public static Page build(Object object) {
        return build(object, new DefaultPageVisitor());
    }

    private Page createPageFromObject(Object object) {
        final Page page = createPageFromClassAnnotations(object);
        return createPageFromMethodsAnnotations(page, object);
    }

    private Page createPageFromClassAnnotations(Object object) {
        return new Page("");
    }

    private Page createPageFromMethodsAnnotations(Page page, Object object) {
        final Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            for (PageAnnotations pageAnnotations : PageAnnotations.values()) {
                final Annotation annotation = method.getAnnotation(pageAnnotations.getAnnotationClass());
                if (annotation != null)
                    page = pageAnnotations.visit(new PageBuilderMetaInfo(visitor, page), annotation);
            }
            page.setContent(createPageContent(object, method, page));
        }
        return page;
    }

    private PageResponse createPageContent(Object object, Method method, Page page) {
        return (request, response) -> {
            try {
                final Object[] args = createArgs(method, request, response, page);
                if (method.getReturnType() == void.class) {
                    method.invoke(object, args);
                    return null;
                } else {
                    return method.invoke(object, args).toString();
                }
            } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private Object[] createArgs(Method method, HttpServletRequest request, HttpServletResponse response, Page page) throws IOException {
        final TypeVariable<Method>[] parameterType = method.getTypeParameters();
        final Object[] args = new Object[parameterType.length];
        for (int i = 0; i < parameterType.length; i++) {
            final TypeVariable<Method> typeVariable = parameterType[i];
            args[i] = createArg(typeVariable, request, response, page);
        }
        return args;
    }

    private Object createArg(TypeVariable<Method> typeVariable, HttpServletRequest request, HttpServletResponse response, Page page) throws IOException {
        Object object = createArgInstance(typeVariable, request, response);
        for (PageParameterAnnotations pageParameterAnnotations : PageParameterAnnotations.values()) {
            final Annotation annotation = typeVariable.getAnnotation(pageParameterAnnotations.getAnnotationClass());
            if (annotation != null)
                object = pageParameterAnnotations.visit(new PageBuilderMetaInfo(visitor, page, request, response, object), annotation);
        }
        return object;
    }

    private Object createArgInstance(TypeVariable<Method> typeVariable, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String typeName = typeVariable.getTypeName();
        if (typeName.equals(HttpServletRequest.class.getName())) {
            return request;
        } else if (typeName.equals(HttpServletResponse.class.getName())) {
            return response;
        } else if (typeName.equals(String.class.getName())) {
            return IOUtils.toString(request.getReader());
        }
        return null;
    }
}
