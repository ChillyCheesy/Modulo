package com.owle.hometracker;

import com.owle.hometracker.events.EventContainer;
import com.owle.hometracker.modules.ModuleContainer;
import com.owle.hometracker.signals.SignalContainer;
import com.owle.hometracker.utils.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModuloAPITest {

    @Test
    public final void testSingletonGetters() {
        final ModuleContainer moduleContainer = ModuloAPI.getModule();
        final EventContainer eventContainer = ModuloAPI.getEvent();
        final SignalContainer signalContainer = ModuloAPI.getSignal();
        final Log log = ModuloAPI.getLogger();
        final ModuloAPI moduloAPI = (ModuloAPI) ModuloAPI.getHTAPI();

        assertEquals(moduleContainer, ModuloAPI.getModule());
        assertEquals(eventContainer, ModuloAPI.getEvent());
        assertEquals(signalContainer, ModuloAPI.getSignal());
        assertEquals(log, ModuloAPI.getLogger());
        assertEquals(moduloAPI, ModuloAPI.getHTAPI());
    }

}
