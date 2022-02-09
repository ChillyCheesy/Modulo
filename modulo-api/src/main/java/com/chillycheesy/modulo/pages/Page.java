package com.chillycheesy.modulo.pages;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

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

    protected Supplier<String> content;

    public Page(HttpRequest requestType, String path, Supplier<String> content) {
        this.requestType = requestType;
        this.path = path;
        this.subpages = new ArrayList<>();
        this.content = content;
    }

    public Page(String path, Supplier<String> content) {
        this(HttpRequest.ANY, path, content);
    }

    public Page(HttpRequest requestType, String path) {
        this(requestType, path, () -> "");
    }

    public Page(String path) {
        this(HttpRequest.ANY, path);
    }

    public Page addSubPage(Page subpage) {
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

    public void setContent(Supplier<String> content) {
        this.content = content;
    }

    public String getContent() {
        return content.get();
    }

    public byte[] getEncryptedContent() {
        return Base64.getEncoder().encode(this.getContent().getBytes());
    }

    public boolean hasChild(Page searchPage) {
        if (this.equals(searchPage)) {
            return true;
        }
        for (Page page : subpages) {
            if (page.equals(searchPage) || page.hasChild(searchPage)) {
                return true;
            }
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
}
