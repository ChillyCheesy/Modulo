package fr.owle.hometracker.event;

import fr.owle.hometracker.modules.HTModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ModuleStatusEventTest {


    private HTModule module1, module2;
    private boolean cancel;

    @BeforeEach
    public final void init() {
        module1 = mock(HTModule.class);
        module2 = mock(HTModule.class);
        cancel = true;
    }

    @Test
    public final void testConstructor() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        constructorTest(OnLoadEvent.class);
        constructorTest(OnStartEvent.class);
        constructorTest(OnStopEvent.class);
    }

    public final void constructorTest(Class<? extends ModuleStatusEvent> eventClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final ModuleStatusEvent logEventTest = eventClass.getConstructor(HTModule.class).newInstance(module1);
        assertEquals(module1, logEventTest.getModule());
    }

    @Test
    public final void testGettersAndSetters() {
        getterSetterTest(new OnLoadEvent(null));
        getterSetterTest(new OnStartEvent(null));
        getterSetterTest(new OnStopEvent(null));
    }


    @Test
    public void equalsTest() {
        final OnLoadEvent logEvent1 = new OnLoadEvent(module1);
        final OnLoadEvent logEvent2 = new OnLoadEvent(module1);
        assertEquals(logEvent1, logEvent2);
        assertNotEquals(logEvent1, new OnLoadEvent(module2));
    }

    public final void getterSetterTest(ModuleStatusEvent statusEvent) {
        statusEvent.setModule(module1);
        assertEquals(module1, statusEvent.getModule());
        statusEvent.setModule(module2);
        assertEquals(module2, statusEvent.getModule());
        assertFalse(statusEvent.isCanceled());
        statusEvent.setCanceled(cancel);
        assertTrue(statusEvent.isCanceled());
    }

}
