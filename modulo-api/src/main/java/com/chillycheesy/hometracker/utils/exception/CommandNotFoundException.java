package com.chillycheesy.hometracker.utils.exception;

import com.chillycheesy.hometracker.commands.CommandFlow;

public class CommandNotFoundException extends CommandException {

    public CommandNotFoundException(CommandFlow flux, String commandName) {
        super(flux, Math.max(0, flux.getContent().indexOf(commandName)), "Command '" + commandName + "' not found");
    }
}
