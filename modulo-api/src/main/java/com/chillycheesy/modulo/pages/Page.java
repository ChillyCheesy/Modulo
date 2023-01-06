package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.pages.comparator.RequestMatcher;
import com.chillycheesy.modulo.pages.comparator.StrictMethodRequestMatcher;
import com.chillycheesy.modulo.pages.comparator.StrictPathRequestMatcher;
import com.chillycheesy.modulo.pages.response.ResponseHandler;
import com.chillycheesy.modulo.utils.Priority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Page {

    protected String name;
    protected String path;
    protected String method;
    protected int priority;
    protected List<RequestMatcher> requestMatcher;
    protected ResponseHandler responseHandler;

    public Page(String name, String path, String method, ResponseHandler responseHandler, List<RequestMatcher> requestMatcher, int priority) {
        this.name = name;
        this.path = path;
        this.method = method;
        this.priority = priority;
        this.requestMatcher = requestMatcher;
        this.responseHandler = responseHandler;
    }

    public Page(String name, String path, String method, ResponseHandler responseHandler, List<RequestMatcher> requestMatcher) {
        this(name, path, method, responseHandler, requestMatcher, Priority.NEUTRAL);
    }

    public Page(String name, String path, String method, ResponseHandler responseHandler) {
        this(name, path, method, responseHandler, new ArrayList<>(List.of(new StrictPathRequestMatcher(), new StrictMethodRequestMatcher())));
    }

    public Page(String name, String path, ResponseHandler responseHandler) {
        this(name, path, "GET", responseHandler);
    }

    public Page() {
        this("", "", "", null);
    }

    public boolean isMatch(HttpServletRequest request) {
        return this.requestMatcher.stream().allMatch(requestMatcher -> requestMatcher.compare(request, this));
    }

    public boolean response(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return responseHandler.response(this, request, response);
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        final Pattern pattern = Pattern.compile("[^/].+");
        final Matcher matcher = pattern.matcher(path);
        return "/" + (matcher.find() ? matcher.group() : "");
    }

    public int getPriority() {
        return priority;
    }

    public List<RequestMatcher> getPathComparator() {
        return requestMatcher;
    }

    public ResponseHandler getResponseHandler() {
        return responseHandler;
    }

    public String getMethod() {
        return method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setPathComparator(List<RequestMatcher> requestMatcher) {
        this.requestMatcher = requestMatcher;
    }

    public void setPageResponse(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}