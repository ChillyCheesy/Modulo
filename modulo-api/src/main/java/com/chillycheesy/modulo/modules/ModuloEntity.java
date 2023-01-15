package com.chillycheesy.modulo.modules;

public interface ModuloEntity<M extends Module> {
    void load(M module);
    void start();
    void stop();
}
