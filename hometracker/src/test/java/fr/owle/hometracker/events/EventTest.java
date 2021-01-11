package fr.owle.hometracker.events;

import fr.owle.hometracker.modules.HTModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class EventTest {

    private HTModule htModule;

    @BeforeEach
    public final void init() {
        htModule = mock(HTModule.class);
    }

    @Test
    public final void testConstructor() {
        final Event event1 = new Event();
        assertNull(event1.getEmitter());
        final Event event2 = new Event(htModule);
        assertEquals(htModule, event2.getEmitter());
    }

    @Test
    public final void testGetterAndSetter() {
        final Event event = new Event();
        event.setEmitter(htModule);
        assertEquals(htModule, event.getEmitter());
    }

}
