package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.Manager;

public class CommandManager extends Manager<Command> {

    private CommandProcessor processor;

    public CommandManager(OperatorManager operatorManager) {
        super();
        processor = new CommandProcessor(this, operatorManager);
    }

    public CommandFlux applyCommand(Module caller, String line) {
        final CommandFlux flux = FluxBuilder.create(line);
        return processor.execute(caller, flux);
    }

    public CommandFlux applyCommand(String line) {
        return applyCommand(null, line);
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
