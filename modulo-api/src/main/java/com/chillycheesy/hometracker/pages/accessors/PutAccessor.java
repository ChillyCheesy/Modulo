package com.chillycheesy.hometracker.pages.accessors;

import com.chillycheesy.hometracker.pages.HttpRequest;

public abstract class PutAccessor extends Accessor {
    public PutAccessor(String subpath) {
        super(HttpRequest.PUT, subpath);
    }
}
