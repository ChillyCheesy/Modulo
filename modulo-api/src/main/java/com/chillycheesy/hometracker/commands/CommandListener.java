package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.modules.Module;

public interface CommandListener {
    CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux);
}
