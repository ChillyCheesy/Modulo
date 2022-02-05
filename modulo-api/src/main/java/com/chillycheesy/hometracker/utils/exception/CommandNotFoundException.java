package com.chillycheesy.hometracker.utils.exception;

import com.chillycheesy.hometracker.commands.CommandFlux;

public class CommandNotFoundException extends CommandException {

    public CommandNotFoundException(CommandFlux flux, String commandName) {
        super(flux, flux.getContent().indexOf(commandName), "Command '" + commandName + "' not found");
    }
}
