package com.chillycheesy.modulo.commands;

import com.chillycheesy.modulo.commands.builder.CommandBuilder;
import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.commands.builder.CommandFlowBuilder;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.Manager;
import com.chillycheesy.modulo.utils.exception.CommandException;

/**
 * This class is responsible for managing all the commands.
 */
public class CommandManager extends Manager<Command> {

    private CommandProcessor processor;

    public CommandManager(OperatorManager operatorManager) {
        super();
        processor = new CommandProcessor(this, operatorManager);
    }

    /**
     * Registers a command to the manager.
     * The command can be any object. It will be built by the {@link CommandBuilder}.
     * @param module The module that is registering the command.
     * @param item The command to register.
     * @return True if the command was registered, false otherwise.
     */
    public boolean registerItemToBuild(Module module, Object item) {
        final Command command = CommandBuilder.build(item);
        return super.registerItem(module, command);
    }

    /**
     * Registers commands list to the manager.
     * The command can be any object. It will be built by the {@link CommandBuilder}.
     * @param module The module that is registering the command.
     * @param items The commands to register.
     * @return True if the command was registered, false otherwise.
     */
    public boolean registerItemToBuild(Module module, Object...items) {
        boolean success = true;
        for (Object item : items)
            if (!this.registerItemToBuild(module, item))
                success = false;
        return success;
    }

    /**
     * This method run a command.
     * @param caller The caller of the command.
     * @param aliasManager The alias manager that is running the command.
     * @param line The line to run.
     * @return The command flow (the command result).
     * @throws CommandException If the command failed.
     */
    public CommandFlow applyCommand(Module caller, AliasManager aliasManager, String line) throws CommandException {
        final CommandFlow flux = CommandFlowBuilder.create(aliasManager, line);
        return processor.execute(caller, flux);
    }

    /**
     * This method run a command.
     * @param aliasManager The alias manager that is running the command.
     * @param line The line to run.
     * @return The command flow (the command result).
     * @throws CommandException If the command failed.
     */
    public CommandFlow applyCommand(AliasManager aliasManager, String line) throws CommandException {
        return applyCommand(null, aliasManager, line);
    }

    /**
     * This method run a command.
     * @param line The line to parse.
     * @return The command flow (the command result).
     * @throws CommandException If the command failed.
     */
    public CommandFlow applyCommand(String line) throws CommandException {
        final AliasManager rootAliasManager = ModuloAPI.getCommand().getMainAliasManager();
        return applyCommand(rootAliasManager, line);
    }

    /**
     * This method is used to get the command by its label.
     * @param label The label of the command.
     * @return The command.
     */
    public Command getCommandByLabel(String label) {
        for (Command command : super.getAllItems()) {
            if (command.getLabel().equalsIgnoreCase(label)) {
                return command;
            }
        }
        return null;
    }

    public CommandProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(CommandProcessor processor) {
        this.processor = processor;
    }

    public CommandProcessor getCommandProcessor() {
        return processor;
    }

    public void setCommandProcessor(CommandProcessor processor) {
        this.processor = processor;
    }

}
