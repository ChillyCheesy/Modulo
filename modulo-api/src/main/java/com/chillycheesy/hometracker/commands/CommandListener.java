package com.chillycheesy.hometracker.commands;

public interface CommandListener {
    boolean onCommand(Module caller, String label, String[] args);
}
