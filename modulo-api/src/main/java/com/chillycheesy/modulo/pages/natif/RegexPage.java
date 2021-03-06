package com.chillycheesy.modulo.pages.natif;

import com.chillycheesy.modulo.pages.HttpRequestType;
import com.chillycheesy.modulo.pages.Page;
import com.chillycheesy.modulo.pages.PageResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPage extends Page {

    public RegexPage(HttpRequestType requestType, String path, PageResponse content) {
        super(requestType, path, content);
    }

    public RegexPage(HttpRequestType requestType, String path, String content) {
        super(requestType, path, content);
    }

    public RegexPage(String path, String content) {
        super(path, content);
    }

    public RegexPage(String path, PageResponse content) {
        super(path, content);
    }

    public RegexPage(HttpRequestType requestType, String path) {
        super(requestType, path);
    }

    public RegexPage(String path) {
        super(path);
    }

    @Override
    public Page redirect(HttpRequestType httpRequest, String subpath) {
        final Pattern pattern = Pattern.compile("^" + this.getPath());
        final Matcher matcher = pattern.matcher(subpath.replaceAll("^/|/$", ""));
        subpath = subpath.replaceAll("^/|/$", "");
        if (matcher.find() && (this.requestType.equals(httpRequest) || this.requestType.equals(HttpRequestType.ANY))) {
            for (Page page : this.subpages) {
                final Page redirection = page.redirect(httpRequest, subpath.substring(this.path.length() + 1));
                if (redirection != null) return redirection;
            }
            return this;
        }
        return null;
    }
}
