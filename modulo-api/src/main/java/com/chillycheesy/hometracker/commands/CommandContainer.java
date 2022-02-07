package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorManager;

public class CommandContainer {

    private CommandManager commandManager;
    private OperatorManager operatorManager;
    private AliasManager mainAliasManager;

    public CommandManager getCommandManager() {
        return commandManager = commandManager == null ? new CommandManager(getOperatorManager()) : commandManager;
    }

    public OperatorManager getOperatorManager() {
        return operatorManager = operatorManager == null ? new OperatorManager() : operatorManager;
    }

    public AliasManager getMainAliasManager() {
        return mainAliasManager = mainAliasManager == null ? new AliasManager() : mainAliasManager;
    }



}
