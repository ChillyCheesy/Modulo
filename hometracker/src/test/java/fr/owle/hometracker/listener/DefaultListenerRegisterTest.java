package fr.owle.hometracker.listener;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class DefaultListenerRegisterTest {

    @Test
    public final void registerTest() {
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer();
        defaultListenerRegisterer.register(mock(HTAPI.class));
        final EventManager eventManager = HTAPI.getEvent().getEventManager();
        assertEquals(1, eventManager.getAllListener().size());
    }


}
