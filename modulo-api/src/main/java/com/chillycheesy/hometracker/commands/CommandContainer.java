package com.chillycheesy.hometracker.commands;

public class CommandContainer {

    private CommandManager commandManager;
    private OperatorManager operatorManager;

    public CommandManager getCommandManager() {
        return commandManager = commandManager == null ? new CommandManager(getOperatorManager()) : commandManager;
    }

    public OperatorManager getOperatorManager() {
        return operatorManager = operatorManager == null ? new OperatorManager() : operatorManager;
    }

}
