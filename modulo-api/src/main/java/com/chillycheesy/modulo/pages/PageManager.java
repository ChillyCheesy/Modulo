package com.chillycheesy.modulo.pages;

import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Manager;
import com.chillycheesy.modulo.utils.exception.No404SubPageException;

import java.util.*;

/**
 * Class that manage the different {@link Page} of the different {@link Module}.
 * It allows you to add, remove, get any {@link Page} from any {@link Module}.
 *
 * @author Geoffrey Vaniscotte
 */
public class PageManager extends Manager<Page> implements RoutingRedirection {

    @Override
    public Page redirect(HttpRequest httpRequest, String path) throws No404SubPageException {
        if (path.startsWith("/")) return redirect(httpRequest, path.substring(1));
        for (List<Page> pages : this.managedItems.values()) {
            for (Page page : pages) {
                final Page redirection = page.redirect(httpRequest, path);
                if (redirection != null) {
                    return redirection;
                }
            }
        }
        if (path.equals("*")) throw new No404SubPageException();
        return redirect(httpRequest, "*");
    }

    @Override
    public Module getModuleByItem(Page searchPage) {
        for (Module module : managedItems.keySet()) {
            final List<Page> pages = managedItems.get(module);
            if (pages.contains(searchPage))
                return module;
            for (Page page : pages) {
                if (page.hasChild(searchPage)) {
                    return module;
                }
            }
        }
        return null;
    }
}