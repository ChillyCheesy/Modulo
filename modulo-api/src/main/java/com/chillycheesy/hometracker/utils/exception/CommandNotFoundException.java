package com.chillycheesy.hometracker.utils.exception;

import com.chillycheesy.hometracker.commands.CommandFlux;

public class CommandNotFoundException extends CommandException {

    public CommandNotFoundException(CommandFlux flux, int index, String commandName) {
        super(flux, index, "Command '" + commandName + "' not found");
    }
}
