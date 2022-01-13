package com.owle.hometracker.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class LogEventTest {

    private String message1, message2;
    private boolean cancel;

    @BeforeEach
    public final void init() {
        message1 = "message 1";
        message2 = "message 2";
        cancel = true;
    }

    @Test
    public final void constructorTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        constructorTest(DebugLogEvent.class);
        constructorTest(ErrorLogEvent.class);
        constructorTest(InfoLogEvent.class);
        constructorTest(WarnLogEvent.class);
    }

    @Test
    public final void getterAndSetterTest() {
        getterSetterTest(new DebugLogEvent(null));
        getterSetterTest(new ErrorLogEvent(null));
        getterSetterTest(new InfoLogEvent(null));
        getterSetterTest(new WarnLogEvent(null));
    }

    @Test
    public void equalsTest() {
        final DebugLogEvent debugLogEvent1 = new DebugLogEvent(message1);
        final DebugLogEvent debugLogEvent2 = new DebugLogEvent(message1);
        assertEquals(debugLogEvent1, debugLogEvent2);
        assertNotEquals(debugLogEvent1, new DebugLogEvent(message2));
    }

    public final void constructorTest(Class<? extends LogEvent> logEventClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final LogEvent logEventTest = logEventClass.getConstructor(String.class).newInstance(message1);
        assertEquals(message1, logEventTest.getMessage());
    }

    public final void getterSetterTest(LogEvent logEvent) {
        logEvent.setMessage(message1);
        assertEquals(message1, logEvent.getMessage());
        logEvent.setMessage(message2);
        assertEquals(message2, logEvent.getMessage());
        assertFalse(logEvent.isCanceled());
        logEvent.setCanceled(cancel);
        assertTrue(logEvent.isCanceled());
    }



}
