package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;

/**
 * Interface for a command listener.
 */
public interface CommandListener {

    /**
     * Called when a command is received.
     *
     * @param caller The module that called the command.
     * @param label The label of the command.
     * @param args The arguments of the command.
     * @param flow The flow of the command.
     * @return The result of the command.
     * @throws CommandException If the command fails.
     */
    CommandFlow onCommand(Module caller, String label, String[] args, CommandFlow flow) throws CommandException;
}
