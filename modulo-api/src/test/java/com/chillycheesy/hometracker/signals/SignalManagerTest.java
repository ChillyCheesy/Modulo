package com.chillycheesy.hometracker.signals;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.event.SignalEmitEvent;
import com.chillycheesy.hometracker.events.EventHandler;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.listener.Listener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class SignalManagerTest {

    static class TestSignalListener implements Listener {
        @SignalHandler("my-signal")
        public void onTest(Module module, String[] args) { }
    }

    static class TestEventListener implements Listener {
        @EventHandler
        public void onTest(SignalEmitEvent event) { }
    }

    private SignalManagerTest.TestSignalListener listener;
    private SignalManager signalManager;
    private HashMap<Module, List<Listener>> map;

    @BeforeEach
    public final void init() throws NoSuchFieldException {
        map = new HashMap<>();
        listener = mock(SignalManagerTest.TestSignalListener.class, CALLS_REAL_METHODS);
        signalManager = mock(SignalManager.class, CALLS_REAL_METHODS);
        FieldSetter.setField(signalManager, SignalManager.class.getDeclaredField("listeners"), map);
    }


    @Test
    public final void emitTest() {
        verify(listener, never()).onTest(any(Module.class), any(String[].class));

        final Module module = mock(Module.class);
        final String[] args = new String[]{"Ee chee wa maa !", "yub nub"};
        final TestEventListener testEventListener = mock(TestEventListener.class, CALLS_REAL_METHODS);
        ModuloAPI.getEvent().getEventManager().registerEventListener(module, testEventListener);

        signalManager.registerSignalListener(module, listener);

        signalManager.emitSignal(module, "message", args);
        verify(listener, never()).onTest(any(Module.class), eq(args));

        signalManager.emitSignal(module, "my-signal", args);
        verify(listener, times(1)).onTest(eq(module), eq(args));
    }

    @Test
    public final void emitEventTest() {
        verify(listener, never()).onTest(any(Module.class), any(String[].class));

        final Module module = mock(Module.class);
        final String[] args = new String[]{"Ee chee wa maa !", "yub nub"};
        final TestEventListener testEventListener = mock(TestEventListener.class, CALLS_REAL_METHODS);
        ModuloAPI.getEvent().getEventManager().registerEventListener(module, testEventListener);

        signalManager.registerSignalListener(module, listener);

        signalManager.emitSignal(module, "my-signal", args);
        verify(listener, times(1)).onTest(eq(module), eq(args));
        final SignalEmitEvent testEvent = new SignalEmitEvent(module, new Module[]{module}, "my-signal", args);
        verify(testEventListener, times(1)).onTest(eq(testEvent));
    }

    @Test
    public final void testRegisterAndRemoveEventListener() {
        final Module module = mock(Module.class);
        final Listener listener1 = mock(Listener.class);
        final Listener listener2 = mock(Listener.class);

        assertTrue(signalManager.registerSignalListener(module, listener1));
        assertTrue(signalManager.registerSignalListener(module, listener2));
        assertTrue(map.containsKey(module));
        assertTrue(map.get(module).contains(listener1));
        assertTrue(map.get(module).contains(listener2));

        assertTrue(signalManager.removeSignalListener(module, listener1));
        assertFalse(map.get(module).contains(listener1));
        assertTrue(map.get(module).contains(listener2));
        assertTrue(signalManager.removeSignalListener(module, listener2));
        assertFalse(map.get(module).contains(listener2));
    }

}
