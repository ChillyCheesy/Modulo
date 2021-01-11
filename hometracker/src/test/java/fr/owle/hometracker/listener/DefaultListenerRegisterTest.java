package fr.owle.hometracker.listener;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultListenerRegisterTest {

    @Test
    public final void registerTest() {
        final EventManager eventManager = mock(EventManager.class);
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer(eventManager);
        final HTAPI htapi = mock(HTAPI.class);
        defaultListenerRegisterer.register(htapi);

        verify(eventManager, times(1)).registerEventListener(eq(htapi), any(HTAPIPageRequestListener.class));
    }

    @Test
    public final void defaultEventManagerTest() {
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer();
        assertEquals(HTAPI.getEvent().getEventManager(), defaultListenerRegisterer.getEventManager());
    }




}
