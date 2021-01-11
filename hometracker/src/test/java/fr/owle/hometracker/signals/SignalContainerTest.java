package fr.owle.hometracker.signals;

import fr.owle.hometracker.HTAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignalContainerTest {

    @Test
    public final void testSingletonGetters() {
        final SignalContainer signalContainer = HTAPI.getSignal();

        final SignalManager signalManager = signalContainer.getSignalManager();

        assertEquals(signalManager, signalContainer.getSignalManager());
    }

}
