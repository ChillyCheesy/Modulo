package com.chillycheesy.modulo.pages.subpages;

import com.chillycheesy.modulo.pages.HttpRequest;
import com.chillycheesy.modulo.pages.Page;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPage extends Page {

    public RegexPage(HttpRequest requestType, String path, Supplier<String> content) {
        super(requestType, path, content);
    }

    public RegexPage(String path, Supplier<String> content) {
        super(path, content);
    }

    public RegexPage(HttpRequest requestType, String path) {
        super(requestType, path);
    }

    public RegexPage(String path) {
        super(path);
    }

    @Override
    public Page redirect(HttpRequest httpRequest, String subpath) {
        final Pattern pattern = Pattern.compile("^" + this.getPath());
        final Matcher matcher = pattern.matcher(subpath.replaceAll("^/|/$", ""));
        subpath = subpath.replaceAll("^/|/$", "");
        if (matcher.find() && (this.requestType.equals(httpRequest) || this.requestType.equals(HttpRequest.ANY))) {
            for (Page page : this.subpages) {
                final Page redirection = page.redirect(httpRequest, subpath.substring(this.path.length() + 1));
                if (redirection != null) return redirection;
            }
            return this;
        }
        return null;
    }
}
