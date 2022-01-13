package com.owle.hometracker.events;

import com.owle.hometracker.ModuloAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventContainerTest {

    @Test
    public final void testSingletonGetters() {
        final EventContainer eventContainer = ModuloAPI.getEvent();

        final EventManager eventManager = eventContainer.getEventManager();

        assertEquals(eventManager, eventContainer.getEventManager());

    }

}
