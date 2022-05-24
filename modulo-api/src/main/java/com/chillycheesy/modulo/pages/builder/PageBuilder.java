package com.chillycheesy.modulo.pages.builder;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        for (Method method : methods)
            if (method.isAnnotationPresent(HttpRequest.class)) {
                for (PageAnnotations pageAnnotations : PageAnnotations.values()) {
                    final Annotation annotation = method.getDeclaredAnnotation(pageAnnotations.getAnnotationClass());
                    if (annotation != null) page = pageAnnotations.visit(new PageBuilderMetaInfo(visitor, page), annotation);
                }
                final Page subpage = page.getLastChild();
                subpage.setContent(createPageContent(object, method, subpage));
            }
        return page;
    }

    private PageResponse createPageContent(Object object, Method method, Page page) {
        return (request, response) -> {
            try {
                final Object[] args = createArgs(method, request, response, page);
                if (method.getReturnType().equals(void.class)) {
                    method.invoke(object, args);
                } else if (method.getReturnType().equals(String.class)) {
                    return method.invoke(object, args).toString();
                } else {
                    //TODO: return json format
                    return method.invoke(object, args).toString();
                }
            } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private Object[] createArgs(Method method, HttpServletRequest request, HttpServletResponse response, Page page) throws IOException {
        final Class<?>[] parameterType = method.getParameterTypes();
        final Annotation[][] annotations = method.getParameterAnnotations();
        final Object[] args = new Object[parameterType.length];
        for (int i = 0 ; i < parameterType.length ; ++i)
            args[i] = createArg(parameterType[i], annotations[i], request, response, page);
        return args;
    }

    private Object createArg(Class<?> type, Annotation[] annotations, HttpServletRequest request, HttpServletResponse response, Page page) throws IOException {
        Object object = createArgInstance(type, request, response);
        for (PageParameterAnnotations pageParameterAnnotations : PageParameterAnnotations.values()) {
            for (Annotation annotation : annotations) {
                if (pageParameterAnnotations.getAnnotationClass().equals(annotation.annotationType())) {
                    object = pageParameterAnnotations.visit(new PageBuilderMetaInfo(visitor, page, request, response, type, object), annotation);
                }
            }
        }
        return object;
    }

    private Object createArgInstance(Class<?> type, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (type.equals(HttpServletRequest.class)) {
            return request;
        } else if (type.equals(HttpServletResponse.class)) {
            return response;
        } else if (type.equals(String.class)) {
            final BufferedReader reader = request.getReader();
            final StringBuilder requestContent = new StringBuilder();
            String buffer;
            while ((buffer = reader.readLine()) != null) requestContent.append(buffer).append("\n");
            return requestContent.toString();
        } else {
            //TODO: return json format
            return null;
        }
    }
}
