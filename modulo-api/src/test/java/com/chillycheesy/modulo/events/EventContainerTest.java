package com.chillycheesy.modulo.events;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
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
