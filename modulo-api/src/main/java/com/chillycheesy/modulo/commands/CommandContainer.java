package com.chillycheesy.modulo.commands;

import com.chillycheesy.modulo.commands.operator.OperatorManager;

/**
 * This class store all singleton instances of the command classes.
 * You can have access to:
 * <ul>
 *     <li>the {@link CommandManager}.</li>
 *     <li>the {@link OperatorManager}.</li>
 *     <li>the {@link AliasManager}.</li>
 * </ul>
 *
 */
public class CommandContainer {

    private CommandManager commandManager;
    private OperatorManager operatorManager;
    private AliasManager mainAliasManager;

    /**
     * Get the command manager as a singleton.
     * @return the command manager.
     */
    public CommandManager getCommandManager() {
        return commandManager = commandManager == null ? new CommandManager(getOperatorManager()) : commandManager;
    }

    /**
     * Get the operator manager as a singleton.
     * @return the operator manager.
     */
    public OperatorManager getOperatorManager() {
        return operatorManager = operatorManager == null ? new OperatorManager() : operatorManager;
    }

    /**
     * Get the main alias manager as a singleton.
     * @return the main alias manager.
     */
    public AliasManager getMainAliasManager() {
        return mainAliasManager = mainAliasManager == null ? new AliasManager() : mainAliasManager;
    }



}
