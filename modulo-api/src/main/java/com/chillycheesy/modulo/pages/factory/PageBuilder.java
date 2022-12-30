package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.comparator.StrictMethodRequestMatcher;
import com.chillycheesy.modulo.pages.comparator.TemplatePathRequestMatcher;
import com.chillycheesy.modulo.pages.response.ResponseHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class PageBuilder {

    private Class<? extends Page> pageClass;
    private String requestMethod;
    private String path;
    private Method method;
    private ParameterAnnotationApplier parameterAnnotationApplier;

    private ResponseAnnotationApplier responseAnnotationApplier;

    private Object sourceObject;

    public PageBuilder setPageClass(Class<? extends Page> pageClass) {
        this.pageClass = pageClass;
        return this;
    }

    public PageBuilder setSourceObject(Object sourceObject) {
        this.sourceObject = sourceObject;
        return this;
    }

    public PageBuilder setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public PageBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public PageBuilder setMethod(Method method) {
        this.method = method;
        return this;
    }

    public PageBuilder setParameterAnnotationApplier(ParameterAnnotationApplier parameterAnnotationApplier) {
        this.parameterAnnotationApplier = parameterAnnotationApplier;
        return this;
    }

    public PageBuilder setResponseAnnotationApplier(ResponseAnnotationApplier responseAnnotationApplier) {
        this.responseAnnotationApplier = responseAnnotationApplier;
        return this;
    }

    public Page build() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final Constructor<? extends Page> constructor = pageClass.getConstructor();
        final Page page = constructor.newInstance();
        page.setMethod(requestMethod);
        page.setPath(path);
        page.setPathComparator(List.of(new StrictMethodRequestMatcher(), new TemplatePathRequestMatcher()));
        final ResponseHandler responseHandler = ResponseFactory.create(sourceObject, method, parameterAnnotationApplier, responseAnnotationApplier);
        page.setPageResponse(responseHandler);
        return page;
    }

    public Class<? extends Page> getPageClass() {
        return pageClass;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }

    public Method getMethod() {
        return method;
    }

    public ParameterAnnotationApplier getParameterAnnotationApplier() {
        return parameterAnnotationApplier;
    }

    public ResponseAnnotationApplier getResponseAnnotationApplier() {
        return responseAnnotationApplier;
    }

    public Object getSourceObject() {
        return sourceObject;
    }
}
