package com.chillycheesy.hometracker.pages.accessors;

import com.chillycheesy.hometracker.pages.HttpRequest;

public abstract class PostAccessor extends Accessor {
    public PostAccessor(String subpath) {
        super(HttpRequest.POST, subpath);
    }
}
