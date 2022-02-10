package com.chillycheesy.modulo.pages;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <pre>
 * Interface of the pages of <i>HomeTracker</i>
 * All the pages must implement this interface.
 * </pre>
 * @author Geoffrey Vaniscotte
 */
public class Page implements RoutingRedirection {
    /**
     * The path to access this page.
     */
    protected String path;
    /**
     * The http request type (GET, PUT, POST, DELETE)
     */
    protected HttpRequest requestType;
    /**
     * The list of sub pages that can either call a method, return a value.
     * The sub path can be empty "" to represent the default accessors of the page,
     * the sub path can also be "*" to represent the not found page case (404 error).
     *
     * For example if your page index is "skywalker", the default accessors will be the one with an empty sub path ("https://modulo.chillycheesy.com/skywalker"),
     * but we could add the accessors for "anakin", "leia", "luke" that show their own content or call their method (https://modulo.chillycheesy.com/skywalker/anakin),
     * but if we try to reach the path "https://modulo.chillycheesy.com/skywalker/jarjar" and the "jarjar" sub path cannot be found, we will redirect to the accessor with the path "*"
     *
     * The sub path can be a keyword to be handled differently by the system (like "*").
     * the sub path "readme" can represent the document or your Page or Module.
     */
    protected List<Page> subpages;

    protected Page parent;

    protected PageResponse content;

    public Page(HttpRequest requestType, String path, PageResponse content) {
        this.requestType = requestType;
        this.path = path;
        this.subpages = new ArrayList<>();
        this.content = content;
    }

    public Page(HttpRequest requestType, String path, String content) {
        this(requestType, path, (request, response) -> content);
    }

    public Page(String path, String content) {
        this(path, (request, response) -> content);
    }

    public Page(String path, PageResponse content) {
        this(HttpRequest.ANY, path, content);
    }

    public Page(HttpRequest requestType, String path) {
        this(requestType, path, (request, response) -> "");
    }

    public Page(String path) {
        this(HttpRequest.ANY, path);
    }

    public Page addSubPage(Page subpage) {
        subpage.parent = this;
        this.subpages.add(subpage);
        return this;
    }

    public boolean removeSubPage(Object o) {
        return subpages.remove(o);
    }

    public boolean addAllSubPage(Collection<? extends Page> c) {
        return subpages.addAll(c);
    }

    public boolean removeAllSubPage(Collection<?> c) {
        return subpages.removeAll(c);
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        final StringBuilder builder = new StringBuilder();
        if (parent != null) {
            builder.append(parent.getFullPath()).append("/");
        }
        return builder.append(path).toString();
    }

    public void setContent(PageResponse content) {
        this.content = content;
    }

    public String getContent(HttpServletRequest request, HttpServletResponse response, boolean pushInResponse) throws IOException {
        final String builtContent = content.buildBody(request, response);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(builtContent.getBytes());
        if (pushInResponse) IOUtils.copy(inputStream, response.getOutputStream());
        return builtContent;
    }

    public String getContent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return this.getContent(request, response, true);
    }

    public boolean hasChild(Page searchPage) {
        if (this.equals(searchPage)) return true;
        for (Page page : subpages) {
            if (page.equals(searchPage) || page.hasChild(searchPage))
                return true;
        }
        return false;
    }

    public Page redirect(HttpRequest httpRequest, String subpath) {
        subpath = subpath.replaceAll("^/|/$", "");
        if (subpath.startsWith(this.path) && (this.requestType.equals(httpRequest) || this.requestType.equals(HttpRequest.ANY))) {
            for (Page page : this.subpages) {
                final Page redirection = page.redirect(httpRequest, subpath.substring(this.path.length()));
                if (redirection != null) return redirection;
            }
            return this;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(path, page.path) && requestType == page.requestType && Objects.equals(subpages, page.subpages) && Objects.equals(content, page.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, requestType, subpages, content);
    }

    @Override
    public String toString() {
        return "Page{" +
                "path='" + path + '\'' +
                ", requestType=" + requestType +
                ", subpages=" + subpages +
                ", content=" + content +
                '}';
    }
}
