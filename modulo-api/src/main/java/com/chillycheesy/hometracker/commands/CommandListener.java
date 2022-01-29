package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;

public interface CommandListener {
    CommandFlux onCommand(Module caller, String label, String[] args, CommandFlux flux) throws CommandException;
}
