package com.chillycheesy.modulo.event;

import com.chillycheesy.modulo.event.*;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.pages.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class RequestEventTest {

    private String moduleName, pageName, path, param, body;

    @BeforeEach
    public final void init() {
        moduleName = "MyModule";
        pageName = "index";
        path = "/my/path";
        param = "name=myName";
        body = "{\"name\":\"myName\"}";
    }

    @Test
    public final void constructorTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        constructorTest(GetRequestEvent.class);
        constructorTest(UpdateRequestEvent.class);
        constructorTest(DeleteRequestEvent.class);
        constructorTest(PutRequestEvent.class);
        constructorTest(PostRequestEvent.class);
    }

    @Test
    public final void getterAndSetterTest() {
        final GetRequestEvent getRequestEvent = new GetRequestEvent(null, null, null, null, null);
        final UpdateRequestEvent updateRequestEvent = new UpdateRequestEvent(null, null, null, null, null);
        final DeleteRequestEvent deleteRequestEvent = new DeleteRequestEvent(null, null, null, null, null);
        final PutRequestEvent putRequestEvent = new PutRequestEvent(null, null, null, null, null);
        final PostRequestEvent postRequestEvent = new PostRequestEvent(null, null, null, null, null);
        getterAndSetterTest(getRequestEvent);
        getterAndSetterTest(updateRequestEvent);
        getterAndSetterTest(deleteRequestEvent);
        getterAndSetterTest(putRequestEvent);
        getterAndSetterTest(postRequestEvent);
    }

    public final void constructorTest(Class<? extends RequestEvent> requestEventClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, InvocationTargetException {
        final RequestEvent requestEvent = requestEventClass.getConstructor(String.class, String.class, String.class, String.class, String.class)
                .newInstance(moduleName, pageName, path, param, body);
        assertEquals(moduleName, requestEvent.getModuleName());
        assertEquals(pageName, requestEvent.getPageName());
        assertEquals(path, requestEvent.getPath());
        assertEquals(body, requestEvent.getBody());
        assertEquals(param, requestEvent.getParam());
    }

    public final void getterAndSetterTest(RequestEvent requestEvent) {
        requestEvent.setModuleName(moduleName);
        requestEvent.setPageName(pageName);
        requestEvent.setPath(path);
        requestEvent.setParam(param);
        requestEvent.setBody(body);
        assertEquals(moduleName, requestEvent.getModuleName());
        assertEquals(pageName, requestEvent.getPageName());
        assertEquals(path, requestEvent.getPath());
        assertEquals(path, requestEvent.getPath());
        assertEquals(body, requestEvent.getBody());
        assertEquals(param, requestEvent.getParam());

        final Module module = mock(Module.class);
        final Page page = mock(Page.class);
        final String content = "content";

        requestEvent.setModule(module);
        requestEvent.setPage(page);
        requestEvent.setContent(content);
        assertEquals(module, requestEvent.getModule());
        assertEquals(page, requestEvent.getPage());
        assertEquals(content, requestEvent.getContent());
    }

}
