package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;

public abstract class PostSubPage extends Page {
    public PostSubPage(String subpath) {
        super(HttpRequest.POST, subpath);
    }
}
