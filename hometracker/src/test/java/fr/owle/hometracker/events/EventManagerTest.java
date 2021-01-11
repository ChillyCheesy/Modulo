package fr.owle.hometracker.events;

import fr.owle.hometracker.event.ErrorLogEvent;
import fr.owle.hometracker.event.InfoLogEvent;
import fr.owle.hometracker.event.LogEvent;
import fr.owle.hometracker.modules.HTModule;
import fr.owle.hometracker.utils.Listener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventManagerTest {

    static class TestEvent extends Event { }
    static class TestEventListener implements Listener {
        @EventHandler
        public void onTest(TestEvent event) { }
    }

    static class BadTestEventListener implements Listener {
        @EventHandler
        public void onError(ErrorLogEvent e) { }
        @EventHandler
        public void onTest() { }
    }

    private TestEventListener listener;
    private EventManager eventManager;
    private HashMap<HTModule, List<Listener>> map;

    @BeforeEach
    public final void init() throws NoSuchFieldException {
        map = new HashMap<>();
        listener = mock(TestEventListener.class, CALLS_REAL_METHODS);
        eventManager = mock(EventManager.class, CALLS_REAL_METHODS);

        FieldSetter.setField(eventManager, EventManager.class.getDeclaredField("listeners"), map);
    }


    @Test
    public final void emitTest() {
        verify(listener, never()).onTest(any(TestEvent.class));

        final InfoLogEvent infoLogEvent = mock(InfoLogEvent.class, CALLS_REAL_METHODS);
        final HTModule module = mock(HTModule.class);

        eventManager.registerEventListener(module, listener);

        eventManager.emitEvent(module, infoLogEvent);
        assertEquals(module, infoLogEvent.getEmitter());
        verify(listener, never()).onTest(any(TestEvent.class));

        final TestEvent event = new TestEvent();
        eventManager.emitEvent(module, event);
        assertEquals(module, event.getEmitter());
        verify(listener, only()).onTest(eq(event));
    }

    @Test
    public final void registerAndRemoveEventListenerTest() {
        final HTModule module = mock(HTModule.class);
        final Listener listener1 = mock(Listener.class);
        final Listener listener2 = mock(Listener.class);

        assertTrue(eventManager.registerEventListener(module, listener1, listener2));
        assertTrue(map.containsKey(module));
        assertTrue(map.get(module).contains(listener1));
        assertTrue(map.get(module).contains(listener2));

        assertTrue(eventManager.removeEventListener(module, listener1));
        assertFalse(map.get(module).contains(listener1));
        assertTrue(map.get(module).contains(listener2));
        assertTrue(eventManager.removeEventListener(module, listener2));
        assertFalse(map.get(module).contains(listener2));

        assertTrue(eventManager.registerEventListener(module, listener1, listener2));
        assertTrue(map.get(module).contains(listener1));
        assertTrue(map.get(module).contains(listener2));
        assertTrue(eventManager.removeEventListener(module, listener1, listener2));
        assertFalse(map.get(module).contains(listener1));
        assertFalse(map.get(module).contains(listener2));

        map.clear();
        assertFalse(eventManager.removeEventListener(module, listener1));
        assertFalse(eventManager.removeEventListener(module, listener1, listener2));

        when(eventManager.registerEventListener(any(HTModule.class), any(Listener.class))).thenReturn(false);

        assertFalse(eventManager.registerEventListener(module, listener1, listener2));
    }

    @Test
    public final void removeAllListenerForAModuleTest() {
        final HTModule module = mock(HTModule.class);
        final HTModule module2 = mock(HTModule.class);
        final Listener listener1 = mock(Listener.class);
        final Listener listener2 = mock(Listener.class);
        assertTrue(eventManager.registerEventListener(module, listener1, listener2));
        assertTrue(eventManager.registerEventListener(module2, listener1, listener2));
        eventManager.removeAllEventListener(module);
        assertFalse(map.containsKey(module));
        assertTrue(map.containsKey(module2));
    }

    @Test
    public final void badListenerEmittedTest() {
        final BadTestEventListener badTestEventListener = new BadTestEventListener();
        final HTModule module = mock(HTModule.class);
        assertFalse(eventManager.registerEventListener(module, badTestEventListener));
    }

    @Test
    public final void emitCatchTest() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        final EventManager eventManager = mock(EventManager.class, CALLS_REAL_METHODS);
        FieldSetter.setField(eventManager, EventManager.class.getDeclaredField("listeners"), map);
        final InvocationTargetException invocationTargetException = mock(InvocationTargetException.class);
        doThrow(invocationTargetException).when(eventManager).emit(any(HTModule.class), any(Event.class));
        eventManager.emitEvent(mock(HTModule.class), mock(LogEvent.class));
        verify(invocationTargetException, times(1)).printStackTrace();
    }

}
