package com.chillycheesy.hometracker.pages.accessors;

import com.chillycheesy.hometracker.pages.HttpRequest;

public abstract class DefaultAccessor extends Accessor{
    public DefaultAccessor() {
        super(HttpRequest.GET, "");
    }
}
