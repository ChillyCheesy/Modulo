package com.chillycheesy.hometracker.pages;

import com.chillycheesy.hometracker.pages.accessors.Accessor;
import com.chillycheesy.hometracker.pages.exception.No404AccessorException;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * Interface of the pages of <i>HomeTracker</i>
 * All the pages must implement this interface.
 * </pre>
 * @author Geoffrey Vaniscotte
 */
public class Page {
    /**
     * The path to access this page.
     */
    private String path;
    /**
     * The list of sub path that can either call a method, return a value.
     * The sub path can be empty "" to represent the default accessors of the page,
     * the sub path can also be "*" to represent the not found page case (404 error).
     *
     * For example if your page index is "skywalker", the default accessors will be the one with an empty sub path ("https://modulo.chillycheesy.com/skywalker"),
     * but we could add the accessors for "anakin", "leia", "luke" that show their own content or call their method (https://modulo.chillycheesy.com/skywalker/anakin),
     * but if we try to reach the path "https://modulo.chillycheesy.com/skywalker/jarjar" and the "jarjar" sub path cannot be found, we will redirect to the accessor with the path "*"
     *
     * The sub path can be a keyword to be handled differently by the system (like "*").
     * the sub path "readme" can represent the document or your Page or Module.
     */
    private List<Accessor> accessors;
    public Accessor redirect(String subpath) throws No404AccessorException {
        final Optional<Accessor> optionalAccessor = this.accessors.stream().filter(accessor -> accessor.getSubpath().equals(subpath)).findFirst();
        if (optionalAccessor.isEmpty() && subpath.equals("*")) throw new No404AccessorException();
        return optionalAccessor.isPresent() ? optionalAccessor.get() : redirect("*");
    }
}
