package com.chillycheesy.hometracker.pages;

import com.chillycheesy.hometracker.utils.exception.No404SubPageException;

public interface RoutingRedirection {
    public Page redirect(HttpRequest httpRequest, String path) throws No404SubPageException;
}
