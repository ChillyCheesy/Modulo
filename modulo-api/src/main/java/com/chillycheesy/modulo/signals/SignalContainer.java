package com.chillycheesy.modulo.signals;

public class SignalContainer {

    private static SignalManager signalManager;

    public SignalManager getSignalManager() {
        return signalManager = signalManager == null ? new SignalManager() : signalManager;
    }
}
