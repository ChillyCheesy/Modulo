package com.chillycheesy.hometracker.pages;

import java.lang.annotation.*;

/**
 * <pre>
 * {@link Page} annotation to select a resource file or folder for your {@link Page}.
 * You must set the value of this annotation.
 * The value can be either a html file or a folder containing html, css, js files.
 *
 * </pre>
 *
 * @see Page
 *
 * @author Geoffrey Vaniscotte
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Resource {
}
