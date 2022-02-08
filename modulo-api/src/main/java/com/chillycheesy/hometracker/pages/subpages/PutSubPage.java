package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;

public abstract class PutSubPage extends Page {
    public PutSubPage(String subpath) {
        super(HttpRequest.PUT, subpath);
    }
}
