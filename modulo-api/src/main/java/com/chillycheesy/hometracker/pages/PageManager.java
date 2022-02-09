package com.chillycheesy.hometracker.pages;

import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Manager;
import com.chillycheesy.hometracker.utils.exception.No404SubPageException;
import com.chillycheesy.hometracker.utils.exception.PageMissingIndexAnnotationException;
import com.chillycheesy.hometracker.utils.exception.PageNotFoundException;
import com.chillycheesy.hometracker.utils.exception.ResourceNotExistingException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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