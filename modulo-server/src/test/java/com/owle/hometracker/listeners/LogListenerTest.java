package com.owle.hometracker.listeners;

import com.owle.hometracker.ModuloAPI;
import com.owle.hometracker.event.DebugLogEvent;
import com.owle.hometracker.event.ErrorLogEvent;
import com.owle.hometracker.event.InfoLogEvent;
import com.owle.hometracker.event.WarnLogEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LogListenerTest {

    private static LogListener log;

    @BeforeAll
    public static void init() {
        log = mock(LogListener.class, CALLS_REAL_METHODS);
        ModuloAPI.getEvent().getEventManager().registerEventListener(ModuloAPI.getHTAPI(), log);
    }

    @AfterAll
    public static void afterAll() {
        ModuloAPI.getEvent().getEventManager().removeEventListener(ModuloAPI.getHTAPI(), log);
    }

    @Test
    public final void infoTest() {
        ModuloAPI.getLogger().info(ModuloAPI.getHTAPI(), "info");
        verify(log, times(1)).logDisplay(any(InfoLogEvent.class));
    }

    @Test
    public final void warnTest() {
        ModuloAPI.getLogger().warn(ModuloAPI.getHTAPI(), "warn");
        verify(log, times(1)).logDisplay(any(WarnLogEvent.class));
    }

    @Test
    public final void debugTest() {
        ModuloAPI.getLogger().debug(ModuloAPI.getHTAPI(), "debug");
        verify(log, times(1)).logDisplay(any(DebugLogEvent.class));
    }

    @Test
    public final void errorTest() {
        ModuloAPI.getLogger().error(ModuloAPI.getHTAPI(), "error");
        verify(log, times(1)).logDisplay(any(ErrorLogEvent.class));
    }


}
