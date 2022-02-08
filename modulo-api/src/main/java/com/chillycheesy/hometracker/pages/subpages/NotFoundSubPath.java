package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;

public abstract class NotFoundSubPath extends Page {
    public NotFoundSubPath() {
        super(HttpRequest.ANY, "*");
    }
}
