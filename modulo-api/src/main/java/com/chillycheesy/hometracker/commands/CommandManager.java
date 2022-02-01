package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.builder.CommandBuilder;
import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Manager;
import com.chillycheesy.hometracker.utils.exception.CommandException;

public class CommandManager extends Manager<Command> {

    private CommandProcessor processor;

    public CommandManager(OperatorManager operatorManager) {
        super();
        processor = new CommandProcessor(this, operatorManager);
    }

    public boolean registerItemToBuild(Module module, Object item) {
        final Command command = CommandBuilder.build(item);
        return super.registerItem(module, command);
    }

    public boolean registerItemToBuild(Module module, Object...items) {
        boolean success = true;
        for (Object item : items)
            if (!this.registerItemToBuild(module, item))
                success = false;
        return success;
    }

    public CommandFlux applyCommand(Module caller, AliasManager aliasManager, String line) throws CommandException {
        final CommandFlux flux = FluxBuilder.create(line, aliasManager);
        return processor.execute(caller, flux);
    }

    public CommandFlux applyCommand(AliasManager aliasManager, String line) throws CommandException {
        return applyCommand(null, aliasManager, line);
    }

    public CommandFlux applyCommand(String line) throws CommandException {
        final AliasManager rootAliasManager = ModuloAPI.getCommand().getMainAliasManager();
        return applyCommand(rootAliasManager, line);
    }

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
