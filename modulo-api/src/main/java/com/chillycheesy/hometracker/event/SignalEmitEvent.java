package com.chillycheesy.hometracker.event;

import com.chillycheesy.hometracker.events.Event;
import com.chillycheesy.hometracker.modules.Module;

import java.util.Arrays;
import java.util.Objects;

/**
 * Event emits when a signal is emitted.
 * @author henouille
 */
public class SignalEmitEvent extends Event {

    private Module signalEmitter;
    private Module[] receptors;
    private String signal;
    private String[] args;

    /**
     * Create a new SignalEmitEvent.
     * @param signalEmitter The {@link Module} that emits the signal.
     * @param receptors The {@link Module} that will receive the signal and invoke a method.
     * @param signal The signal name that has been emitted.
     * @param args The args of the signal.
     */
    public SignalEmitEvent(Module signalEmitter, Module[] receptors, String signal, String[] args) {
        this.signalEmitter = signalEmitter;
        this.receptors = receptors;
        this.signal = signal;
        this.args = args;
    }

    /**
     * Create a new SignalEmitEvent.
     * @param signalEmitter The {@link Module} that emits the signal.
     * @param signal The signal name that has been emitted.
     * @param args The args of the signal.
     * By calling this constructor, the receptors will be an empty array.
     */
    public SignalEmitEvent(Module signalEmitter, String signal, String[] args) {
        this(signalEmitter, new Module[0], signal, args);
    }

    /**
     * Check if the signal was receive.
     * @return True if at least one {@link Module} has catch the signal.
     */
    public boolean haveReceptors() {
        return receptors.length > 0;
    }

    /**
     * Setter for the emitter module.
     * @param signalEmitter The new signal emitter.
     */
    public void setSignalEmitter(Module signalEmitter) {
        this.signalEmitter = signalEmitter;
    }

    /**
     * Setter for the receptors module.
     * @param receptors The new receptors.
     */
    public void setReceptors(Module[] receptors) {
        this.receptors = receptors;
    }

    /**
     * Getter for the signal emitter.
     * @return The signal emitter.
     */
    public Module getSignalEmitter() {
        return signalEmitter;
    }

    /**
     * Setter for the signal.
     * @param signal The signal.
     */
    public void setSignal(String signal) {
        this.signal = signal;
    }

    /**
     * Setter for the arguments.
     * @param args The arguments.
     */
    public void setArgs(String[] args) {
        this.args = args;
    }

    /**
     * Getter for the receptor.
     * @return The receptor.
     */
    public Module[] getReceptor() {
        return receptors;
    }

    /**
     * Getter for the signal.
     * @return The signal.
     */
    public String getSignal() {
        return signal;
    }

    /**
     * Getter for the arguments.
     * @return The arguments.
     */
    public String[] getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignalEmitEvent emitEvent = (SignalEmitEvent) o;
        return Objects.equals(signalEmitter, emitEvent.signalEmitter) && Arrays.equals(receptors, emitEvent.receptors) && Objects.equals(signal, emitEvent.signal) && Arrays.equals(args, emitEvent.args);
    }
}
