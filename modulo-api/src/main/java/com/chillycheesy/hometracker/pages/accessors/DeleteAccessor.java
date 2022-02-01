package com.chillycheesy.hometracker.pages.accessors;

import com.chillycheesy.hometracker.pages.HttpRequest;

public abstract class DeleteAccessor extends Accessor {
    public DeleteAccessor(String subpath) {
        super(HttpRequest.DELETE, subpath);
    }
}