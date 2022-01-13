package com.owle.hometracker.utils;

import com.owle.hometracker.event.DebugLogEvent;
import com.owle.hometracker.event.ErrorLogEvent;
import com.owle.hometracker.event.InfoLogEvent;
import com.owle.hometracker.event.WarnLogEvent;
import com.owle.hometracker.events.Event;

import com.owle.hometracker.events.EventManager;
import com.owle.hometracker.modules.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LogTest {

//    private static final String MESSAGE = "Ee chee wa maa !";
//
//    @Mock private EventManager eventManager;
//    @Mock private Module module;
//    @Mock private Log log;
//
//    @BeforeEach
//    public final void init() throws NoSuchFieldException {
//        MockitoAnnotations.initMocks(this);
//        FieldSetter.setField(log, Log.class.getDeclaredField("eventManager"), eventManager);
//        doNothing().when(eventManager).emitEvent(any(Module.class), any(Event.class));
//    }
//
//    @Test
//    public final void testInfoLog() {
//        doCallRealMethod().when(log).info(any(), any());
//        log.info(module, MESSAGE);
//        final InfoLogEvent logEvent = new InfoLogEvent(MESSAGE);
//        verify(eventManager, times(1)).emitEvent(module, logEvent);
//    }
//
//    @Test
//    public final void testWarnLog() {
//        doCallRealMethod().when(log).warn(any(), any());
//        log.warn(module, MESSAGE);
//        final WarnLogEvent logEvent = new WarnLogEvent(MESSAGE);
//        verify(eventManager, times(1)).emitEvent(module, logEvent);
//
//    }
//
//    @Test
//    public final void testDebugLog() {
//        doCallRealMethod().when(log).debug(any(), any());
//        log.debug(module, MESSAGE);
//        final DebugLogEvent logEvent = new DebugLogEvent(MESSAGE);
//        verify(eventManager, times(1)).emitEvent(module, logEvent);
//
//    }
//
//    @Test
//    public final void testErrorLog() {
//        doCallRealMethod().when(log).error(any(), any());
//        log.error(module, MESSAGE);
//        final ErrorLogEvent logEvent = new ErrorLogEvent(MESSAGE);
//        verify(eventManager, times(1)).emitEvent(module, logEvent);
//    }

}
