package fr.owle.hometracker.events;

import fr.owle.hometracker.HTAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventContainerTest {

    @Test
    public final void testSingletonGetters() {
        final EventContainer eventContainer = HTAPI.getEvent();

        final EventManager eventManager = eventContainer.getEventManager();

        assertEquals(eventManager, eventContainer.getEventManager());

    }

}
