package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.utils.Manager;

public class CommandManager extends Manager<Command> {

    private CommandProcessor processor;

    public CommandManager(OperatorManager operatorManager) {
        super();
        processor = new CommandProcessor(this, operatorManager);
    }

    public CommandFlux applyCommand(String line) {
        final CommandFlux flux = new CommandFlux(line);
        return processor.execute(flux);
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
