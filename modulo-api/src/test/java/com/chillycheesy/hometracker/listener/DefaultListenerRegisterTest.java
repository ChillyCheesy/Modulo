package com.chillycheesy.hometracker.listener;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.events.EventManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultListenerRegisterTest {

    @Test
    public final void registerTest() {
        final EventManager eventManager = mock(EventManager.class);
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer(eventManager);
        final ModuloAPI moduloAPI = mock(ModuloAPI.class);
        defaultListenerRegisterer.register(moduloAPI);

        verify(eventManager, times(1)).registerEventListener(eq(moduloAPI), any(HTAPIPageRequestListener.class));
    }

    @Test
    public final void defaultEventManagerTest() {
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer();
        assertEquals(ModuloAPI.getEvent().getEventManager(), defaultListenerRegisterer.getEventManager());
    }




}
