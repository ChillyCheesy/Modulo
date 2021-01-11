package fr.owle.hello;

import fr.owle.hello.listeners.ListenerController;
import fr.owle.hello.pages.PageController;
import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.utils.exception.PageMissingIndexAnnotationException;
import fr.owle.hometracker.utils.exception.PageNotFoundException;

public class HelloModule extends HTModule {

    private ListenerController listenerController;
    private PageController pageController;

    private static HelloModule instance;

    public static HelloModule getInstance() {
        return instance;
    }

    @Override
    protected void onLoad() {
        instance = this;
        listenerController = new ListenerController();
        pageController = new PageController();
    }

    @Override
    protected void onStart() throws PageMissingIndexAnnotationException, PageNotFoundException {
        HTAPI.getLogger().info(this, this.getName() + " say hello to you !");
        listenerController.loadListeners(this);
        pageController.loadPages(this);
    }

    @Override
    protected void onStop() {

    }

}
