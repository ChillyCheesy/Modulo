package fr.owle.hometracker.event;

import fr.owle.hometracker.modules.HTModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SignalEmitEventTest {

    private HTModule module1, module2, module3, module4;

    @BeforeEach
    public final void init() {
        module1 = mock(HTModule.class);
        module2 = mock(HTModule.class);
        module3 = mock(HTModule.class);
        module4 = mock(HTModule.class);
    }

    @Test
    public final void constructorsTest() {
        final HTModule[] receptors = {module2, module3, module4};
        final String signalName = "hello";
        final String[] args = {"Ee", "chee", "wa", "maa"};
        final SignalEmitEvent signalEmitEvent = new SignalEmitEvent(module1, receptors, signalName, args);

        assertEquals(module1, signalEmitEvent.getSignalEmitter());
        assertEquals(receptors, signalEmitEvent.getReceptor());
        assertEquals(signalName, signalEmitEvent.getSignal());
        assertEquals(args, signalEmitEvent.getArgs());

        final SignalEmitEvent signalEmitEvent2 = new SignalEmitEvent(module1, signalName, args);
        assertArrayEquals(new HTModule[]{}, signalEmitEvent2.getReceptor());
    }

    @Test
    public final void gettersAndSettersTest() {
        final HTModule emitter = module1;
        final HTModule[] receptors = {module2, module3, module4};
        final String signalName = "hello";
        final String[] args = {"Ee", "chee", "wa", "maa"};

        final SignalEmitEvent signalEmitEvent = new SignalEmitEvent(null, null, null, null);

        signalEmitEvent.setSignalEmitter(emitter);
        signalEmitEvent.setReceptors(receptors);
        signalEmitEvent.setSignal(signalName);
        signalEmitEvent.setArgs(args);

        assertEquals(emitter, signalEmitEvent.getSignalEmitter());
        assertEquals(receptors, signalEmitEvent.getReceptor());
        assertEquals(signalName, signalEmitEvent.getSignal());
        assertEquals(args, signalEmitEvent.getArgs());
    }

    @Test
    public final void haveReceptorsTest() {
        final String signalName = "hello";
        final String[] args = {"Ee", "chee", "wa", "maa"};
        final SignalEmitEvent signalEmitEvent1 = new SignalEmitEvent(module1, new HTModule[]{module2, module3, module4}, signalName, args);
        final SignalEmitEvent signalEmitEvent2 = new SignalEmitEvent(module1, new HTModule[]{module2, module3}, signalName, args);
        final SignalEmitEvent signalEmitEvent3 = new SignalEmitEvent(module1, new HTModule[]{module2}, signalName, args);
        final SignalEmitEvent signalEmitEvent4 = new SignalEmitEvent(module1, new HTModule[]{}, signalName, args);
        final SignalEmitEvent signalEmitEvent5 = new SignalEmitEvent(module1, signalName, args);

        assertTrue(signalEmitEvent1.haveReceptors());
        assertTrue(signalEmitEvent2.haveReceptors());
        assertTrue(signalEmitEvent3.haveReceptors());
        assertFalse(signalEmitEvent4.haveReceptors());
        assertFalse(signalEmitEvent5.haveReceptors());
    }

    @Test
    public final void equalsTest() {
        final HTModule[] receptors = {module2, module3, module4};
        final String signalName = "hello";
        final String[] args = {"Ee", "chee", "wa", "maa"};
        final SignalEmitEvent signalEmitEvent1 = new SignalEmitEvent(module1, receptors, signalName, args);
        final SignalEmitEvent signalEmitEvent2 = new SignalEmitEvent(module1, receptors, signalName, args);
        assertEquals(signalEmitEvent1, signalEmitEvent2);
        assertNotEquals(signalEmitEvent1, new SignalEmitEvent(module2, receptors, signalName, args));
        assertNotEquals(signalEmitEvent1, new SignalEmitEvent(module1, new HTModule[]{}, signalName, args));
        assertNotEquals(signalEmitEvent1, new SignalEmitEvent(module1, receptors, "sku !", args));
        assertNotEquals(signalEmitEvent1, new SignalEmitEvent(module1, receptors, signalName, new String[]{}));

        assertEquals(new SignalEmitEvent(module1, new HTModule[]{}, signalName, args), new SignalEmitEvent(module1, signalName, args));
    }



}
