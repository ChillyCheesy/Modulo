package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.ResourcePage;
import com.chillycheesy.modulo.pages.comparator.RequestMatcher;
import com.chillycheesy.modulo.pages.comparator.StrictMethodRequestMatcher;
import com.chillycheesy.modulo.pages.comparator.TemplatePathRequestMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DefaultMethodAnnotationApplier implements MethodAnnotationApplier {

    @Override
    public PageBuilder applyPageTypeAnnotation(PageType pageType, PageBuilder builder) {
        final Class<? extends Page> type = pageType.value();
        return builder.setPageClass(type);
    }

    @Override
    public PageBuilder applyResponseResourceAnnotation(ResponseResource responseResource, PageBuilder builder) {
        final Class<? extends Page> currentPage = builder.getPageClass();
        if (currentPage == null) {
            return builder
                .setPageClass(ResourcePage.class)
                .addRequestMatcher(new StrictMethodRequestMatcher(), new TemplatePathRequestMatcher());
        }
        return builder;
    }

    @Override
    public PageBuilder applyRequestAnnotation(Request request, PageBuilder builder) {
        final String method = request.method();
        final String path = request.path();
        return builder.setPath(path).setRequestMethod(method);
    }

    @Override
    public PageBuilder applyGetRequestAnnotation(GetRequest request, PageBuilder builder) {
        final String path = request.value();
        return builder.setPath(path).setRequestMethod("GET");
    }

    @Override
    public PageBuilder applyPostRequestAnnotation(PostRequest request, PageBuilder builder) {
        final String path = request.value();
        return builder.setPath(path).setRequestMethod("POST");
    }

    @Override
    public PageBuilder applyPutRequestAnnotation(PutRequest request, PageBuilder builder) {
        final String path = request.value();
        return builder.setPath(path).setRequestMethod("PUT");
    }

    @Override
    public PageBuilder applyDeleteRequestAnnotation(DeleteRequest request, PageBuilder builder) {
        final String path = request.value();
        return builder.setPath(path).setRequestMethod("DELETE");
    }

    @Override
    public PageBuilder applyPriorityAnnotation(Priority priority, PageBuilder builder) {
        final int priorityValue = priority.value();
        return builder.setPriority(priorityValue);
    }

    @Override
    public PageBuilder applyMatcherAnnotation(Matcher matcher, PageBuilder builder) {
        final Class<? extends RequestMatcher>[] matchersClasses = matcher.value();
        for (final Class<? extends RequestMatcher> matcherClass : matchersClasses) {
            try {
                final Constructor<? extends RequestMatcher> constructor = matcherClass.getConstructor();
                final RequestMatcher requestMatcher = constructor.newInstance();
                builder = builder.addRequestMatcher(requestMatcher);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return builder;
    }

}
