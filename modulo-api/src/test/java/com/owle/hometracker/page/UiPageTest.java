package com.owle.hometracker.page;

import com.owle.hometracker.pages.GetRequest;
import com.owle.hometracker.pages.Index;
import com.owle.hometracker.pages.Resource;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UiPageTest {

    @Test
    public final void configurationTest() throws NoSuchMethodException {
        final Index indexAnnotation = UiPage.class.getAnnotation(Index.class);

        final Method method = UiPage.class.getMethod("getInterface");
        final Resource resource = method.getAnnotation(Resource.class);
        final GetRequest getRequest = method.getAnnotation(GetRequest.class);

        assertNotNull(indexAnnotation);
        assertNotNull(resource);
        assertNotNull(getRequest);

        assertEquals("ui", indexAnnotation.value());
        assertEquals("", getRequest.value());
    }

    @Test
    public final void interfaceTest() {
        final UiPage uiPage = new UiPage();
        final String pageInterface = uiPage.getInterface();
        assertEquals("hometracker-ui", pageInterface);
    }

}
