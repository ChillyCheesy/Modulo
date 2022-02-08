package com.chillycheesy.hometracker.pages.subpages;

import com.chillycheesy.hometracker.pages.HttpRequest;
import com.chillycheesy.hometracker.pages.Page;

public abstract class GetSubPage extends Page {

    public GetSubPage(String subpath) {
        super(HttpRequest.GET, subpath);
    }
}
