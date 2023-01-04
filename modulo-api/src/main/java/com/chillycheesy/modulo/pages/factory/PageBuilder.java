package com.chillycheesy.modulo.pages.factory;

import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.comparator.RequestMatcher;
import com.chillycheesy.modulo.pages.comparator.StrictMethodRequestMatcher;
import com.chillycheesy.modulo.pages.comparator.StrictPathRequestMatcher;
import com.chillycheesy.modulo.pages.response.ResponseHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageBuilder {

    private int priority;

    private Class<? extends Page> pageClass;
    private String requestMethod;
    private String path;
    private Method method;
    private ParameterAnnotationApplier parameterAnnotationApplier;

    private ResponseAnnotationApplier responseAnnotationApplier;

    private Object sourceObject;

    private List<RequestMatcher> requestMatchers;

    public PageBuilder() {
        this.priority = 0;
        this.requestMatchers = new ArrayList<>();

    }

    public PageBuilder addRequestMatcher(RequestMatcher ...requestMatchers) {
        this.requestMatchers.addAll(Arrays.asList(requestMatchers));
        return this;
    }

    public PageBuilder clearRequests() {
        this.requestMatchers.clear();
        return this;
    }

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

    public PageBuilder setPriority(int priority) {
        this.priority = priority;
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
        if (pageClass == null) pageClass = Page.class;
        if (requestMethod == null) requestMethod = "GET";
        if (path == null) path = "/";
        if (requestMatchers.isEmpty()) requestMatchers = List.of(new StrictPathRequestMatcher(), new StrictMethodRequestMatcher());
        final Constructor<? extends Page> constructor = pageClass.getConstructor();
        final Page page = constructor.newInstance();
        page.setName(method.getName());
        page.setMethod(requestMethod);
        page.setPath(path);
        page.setPriority(priority);
        page.setPathComparator(requestMatchers);
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
