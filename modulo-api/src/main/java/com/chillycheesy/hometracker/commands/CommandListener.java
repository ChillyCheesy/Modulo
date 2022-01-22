package com.chillycheesy.hometracker.commands;

public interface CommandListener {
    CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux);
}
