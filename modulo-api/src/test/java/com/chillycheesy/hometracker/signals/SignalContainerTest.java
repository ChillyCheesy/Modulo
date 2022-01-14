package com.chillycheesy.hometracker.signals;

import com.chillycheesy.hometracker.ModuloAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SignalContainerTest {

    @Test
    public final void testSingletonGetters() {
        final SignalContainer signalContainer = ModuloAPI.getSignal();

        final SignalManager signalManager = signalContainer.getSignalManager();

        assertEquals(signalManager, signalContainer.getSignalManager());
    }

}
