package com.chillycheesy.modulo.signals;

import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.signals.SignalContainer;
import com.chillycheesy.modulo.signals.SignalManager;
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
