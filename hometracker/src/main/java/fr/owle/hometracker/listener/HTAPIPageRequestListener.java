package fr.owle.hometracker.listener;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.*;
import fr.owle.hometracker.events.Event;
import fr.owle.hometracker.events.EventHandler;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.pages.*;
import fr.owle.hometracker.utils.Listener;
import fr.owle.hometracker.utils.exception.ResourceNotExistingException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * HTAPIPageRequestListener catch in priority the {@link RequestEvent}.
 * @see GetRequestEvent
 * @see PostRequestEvent
 * @see PutRequestEvent
 * @see DeleteRequestEvent
 * @see UpdateRequestEvent
 * @author henouille
 */
public class HTAPIPageRequestListener implements Listener {

    private final PageManager pageManager;

    /**
     * Create a new HTAPIPageRequestListener with
     * the default {@link PageManager}.
     */
    public HTAPIPageRequestListener() {
        this(HTAPI.getPage().getPageManager());
    }

    /**
     * Create a new HTAPIPageRequestListener.
     * @param pageManager The target page manager.
     */
    public HTAPIPageRequestListener(PageManager pageManager) {
        this.pageManager = pageManager;
    }

    /**
     * On get request.
     * @param event The request event.
     */
    @EventHandler(Event.DIVINE)
    public void getRequest(GetRequestEvent event) {
        final Page page = event.getPage();
        final HTModule htModule = event.getModule();
        final String path = event.getPath();
        final String param = event.getParam();
        final String body = event.getBody();
        final String content = defineMessage(htModule, page, path, param, body, GetRequest.class);
        event.setContent(content);
    }

    /**
     * On post request.
     * @param event The request event.
     */
    @EventHandler(Event.DIVINE)
    public void postRequest(PostRequestEvent event) {
        final Page page = event.getPage();
        final HTModule htModule = event.getModule();
        final String path = event.getPath();
        final String param = event.getParam();
        final String body = event.getBody();
        final String content = defineMessage(htModule, page, path, param, body, PostRequest.class);
        event.setContent(content);
    }

    /**
     * On put request.
     * @param event The request event.
     */
    @EventHandler(Event.DIVINE)
    public void putRequest(PutRequestEvent event) {
        final Page page = event.getPage();
        final HTModule htModule = event.getModule();
        final String path = event.getPath();
        final String param = event.getParam();
        final String body = event.getBody();
        final String content = defineMessage(htModule, page, path, param, body, PutRequest.class);
        event.setContent(content);
    }

    /**
     * On delete request.
     * @param event The request event.
     */
    @EventHandler(Event.DIVINE)
    public void deleteRequest(DeleteRequestEvent event) {
        final Page page = event.getPage();
        final HTModule htModule = event.getModule();
        final String path = event.getPath();
        final String param = event.getParam();
        final String body = event.getBody();
        final String content = defineMessage(htModule, page, path, param, body, DeleteRequest.class);
        event.setContent(content);
    }

    private String defineMessage(HTModule htModule, Page page, String path, String param, String body, Class<? extends Annotation> annotationClass) {
        try {
            return pageManager.getPagePathContent(htModule, page, path, param, body, annotationClass);
        } catch (IOException | InvocationTargetException | IllegalAccessException | ResourceNotExistingException e) {
            return "<h1>404</h1><h3><span style=\"color: red\">Error: </span>" + e.getMessage() + "</h1>";
        }
    }

}
