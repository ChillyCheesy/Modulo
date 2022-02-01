package com.chillycheesy.hometracker.pages.accessors;

import com.chillycheesy.hometracker.pages.HttpRequest;

public abstract class GetAccessor extends Accessor {
    public GetAccessor(String subpath) {
        super(HttpRequest.GET, subpath);
    }
}