package fr.owle.hometracker;

import fr.owle.hometracker.events.EventContainer;
import fr.owle.hometracker.modules.ModuleContainer;
import fr.owle.hometracker.signals.SignalContainer;
import fr.owle.hometracker.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTAPITest {

    @Test
    public final void testSingletonGetters() {
        final ModuleContainer moduleContainer = HTAPI.getModule();
        final EventContainer eventContainer = HTAPI.getEvent();
        final SignalContainer signalContainer = HTAPI.getSignal();
        final Log log = HTAPI.getLogger();
        final HTAPI htapi = (HTAPI) HTAPI.getHTAPI();

        assertEquals(moduleContainer, HTAPI.getModule());
        assertEquals(eventContainer, HTAPI.getEvent());
        assertEquals(signalContainer, HTAPI.getSignal());
        assertEquals(log, HTAPI.getLogger());
        assertEquals(htapi, HTAPI.getHTAPI());
    }

}
