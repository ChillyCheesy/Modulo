package fr.owle.hometracker.listeners;

import fr.owle.hometracker.HTAPI;
import fr.owle.hometracker.event.DebugLogEvent;
import fr.owle.hometracker.event.ErrorLogEvent;
import fr.owle.hometracker.event.InfoLogEvent;
import fr.owle.hometracker.event.WarnLogEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LogListenerTest {

    private static LogListener log;

    @BeforeAll
    public static void init() {
        log = mock(LogListener.class, CALLS_REAL_METHODS);
        HTAPI.getEvent().getEventManager().registerEventListener(HTAPI.getHTAPI(), log);
    }

    @AfterAll
    public static void afterAll() {
        HTAPI.getEvent().getEventManager().removeEventListener(HTAPI.getHTAPI(), log);
    }

    @Test
    public final void infoTest() {
        HTAPI.getLogger().info(HTAPI.getHTAPI(), "info");
        verify(log, times(1)).logDisplay(any(InfoLogEvent.class));
    }

    @Test
    public final void warnTest() {
        HTAPI.getLogger().warn(HTAPI.getHTAPI(), "warn");
        verify(log, times(1)).logDisplay(any(WarnLogEvent.class));
    }

    @Test
    public final void debugTest() {
        HTAPI.getLogger().debug(HTAPI.getHTAPI(), "debug");
        verify(log, times(1)).logDisplay(any(DebugLogEvent.class));
    }

    @Test
    public final void errorTest() {
        HTAPI.getLogger().error(HTAPI.getHTAPI(), "error");
        verify(log, times(1)).logDisplay(any(ErrorLogEvent.class));
    }


}
