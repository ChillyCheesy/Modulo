package com.chillycheesy.modulo.events;

import java.io.IOException;

@FunctionalInterface
public interface CancelableAction {
    void action() throws IOException;
}
