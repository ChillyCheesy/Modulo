package com.chillycheesy.hometracker.commands;

public class CommandProcessor {

    private CommandManager commandManager;
    private OperatorManager operatorManager;

    public CommandProcessor(CommandManager commandManager, OperatorManager operatorManager) {
        this.commandManager = commandManager;
        this.operatorManager = operatorManager;
    }

    public CommandFlux execute(CommandFlux flux) {
        return operatorManager.applyOperators(flux);
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public OperatorManager getOperatorManager() {
        return operatorManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setOperatorManager(OperatorManager operatorManager) {
        this.operatorManager = operatorManager;
    }
}
