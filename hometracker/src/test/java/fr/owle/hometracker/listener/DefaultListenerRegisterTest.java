package fr.owle.hometracker.listener;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.events.EventManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultListenerRegisterTest {

    @Test
    public final void registerTest() {
        final DefaultListenerRegisterer defaultListenerRegisterer = new DefaultListenerRegisterer();
        defaultListenerRegisterer.register((HTAPI) HTAPI.getHTAPI());
        final EventManager eventManager = HTAPI.getEvent().getEventManager();
        assertEquals(1, eventManager.getAllListener().size());
    }


}
