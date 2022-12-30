package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PageFactory {

    public static List<Page> createPages(Object object) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return createPages(object, new DefaultMethodAnnotationApplier(), new DefaultParameterAnnotationApplier(), new DefaultResponseAnnotationApplier());
    }

    public static List<Page> createPages(Object object, MethodAnnotationApplier methodApplier, ParameterAnnotationApplier parameterApplier, ResponseAnnotationApplier responseApplier) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final List<Page> pages = new ArrayList<>();
        for (Method method : object.getClass().getDeclaredMethods()) {
            final PageFactory factory = new PageFactory(object, method, methodApplier, parameterApplier, responseApplier);
            final Page page = factory.createPage();
            if (page != null) pages.add(page);
        }
        return pages;
    }

    private final Method method;
    private final MethodAnnotationApplier methodApplier;
    private final ParameterAnnotationApplier parameterApplier;
    private final ResponseAnnotationApplier responseApplier;
    private final Object sourceObject;

    private PageFactory(Object sourceObject, Method method, MethodAnnotationApplier methodApplier, ParameterAnnotationApplier parameterApplier, ResponseAnnotationApplier responseApplier) {
        this.sourceObject = sourceObject;
        this.method = method;
        this.methodApplier = methodApplier;
        this.parameterApplier = parameterApplier;
        this.responseApplier = responseApplier;
    }

    private Page createPage() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final PageBuilder builder = new PageBuilder()
            .setMethod(method)
            .setParameterAnnotationApplier(parameterApplier)
            .setResponseAnnotationApplier(responseApplier)
            .setSourceObject(sourceObject);
        return applyMethodAnnotations(builder).build();
    }

    private PageBuilder applyMethodAnnotations(PageBuilder builder) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof ResponseResource)
                builder = methodApplier.applyResponseResourceAnnotation((ResponseResource) annotation, builder);
            else if (annotation instanceof PageType)
                builder = methodApplier.applyPageTypeAnnotation((PageType) annotation, builder);
            else if (annotation instanceof Request)
                builder = methodApplier.applyRequestAnnotation((Request) annotation, builder);
            else if (annotation instanceof GetRequest)
                builder = methodApplier.applyGetRequestAnnotation((GetRequest) annotation, builder);
            else if (annotation instanceof PostRequest)
                builder = methodApplier.applyPostRequestAnnotation((PostRequest) annotation, builder);
            else if (annotation instanceof PutRequest)
                builder = methodApplier.applyPutRequestAnnotation((PutRequest) annotation, builder);
            else if (annotation instanceof DeleteRequest)
                builder = methodApplier.applyDeleteRequestAnnotation((DeleteRequest) annotation, builder);
        }
        return builder;
    }

}
