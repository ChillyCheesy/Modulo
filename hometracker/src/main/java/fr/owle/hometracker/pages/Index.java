package fr.owle.hometracker.pages;

import java.lang.annotation.*;

/**
 * <pre>
 * Index is an annotation to put on a page class to set it's index.
 * The index of a page is the name of it.
 * It's how the {@link PageManager} will get your page.
 * One module can't submit two pages with the same index.
 *
 * If you submit more than one page, you'll have to name one of them \"index\" so that the {@link PageManager} can find it as the main page of your module.
 * Otherwise, it will get the first page submitted as the main page.
 *
 * There's also a reserved index named \"404\" which is the page you want to display if by mistake, a not existing page is required.
 * Again by default, the {@link PageManager} will send the index page.
 * </pre>
 * @author Geoffrey Vaniscotte
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Index {
    /**
     *
     * @return the index of your page
     * By default the index is \"index\"
     */
    String value() default "index";
}
