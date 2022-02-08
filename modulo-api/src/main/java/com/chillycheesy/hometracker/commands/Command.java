package com.chillycheesy.hometracker.commands;

/**
 * This class represents a command that can be executed by the {@link CommandManager}.
 * A command contain a label, a description, and an example of how to use it named usage.
 * A command can have alias that can be used to execute the command. an alias is an equivalent to the label.
 */
public class Command {

    /**
     * The label of the command.
     */
    protected String label;
    /**
     * The alias list of the command.
     */
    protected String[] alias;
    /**
     * The description of the command.
     */
    protected String description;
    /**
     * The example of how to use the command.
     */
    protected String usage;

    /**
     * The listener of the command.
     * This interface represents the methods to execute when the command is executed.
     */
    protected CommandListener commandListener;

    public Command() { }

    public Command(String label) {
        this.label = label;
    }

    public Command(String label, String[] alias, String description, String usage, CommandListener commandListener) {
        this.label = label;
        this.alias = alias;
        this.description = description;
        this.usage = usage;
        this.commandListener = commandListener;
    }

    /**
     * Get the listener of the command.
     * @return the listener of the command.
     */
    public CommandListener getCommandListener() {
        return commandListener;
    }

    /**
     * Get the description of the command.
     * @return the description of the command.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the example of how to use the command.
     * @return the example of how to use the command.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Get the label of the command.
     * @return the label of the command.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the alias list of the command.
     * @return the alias list of the command.
     */
    public String[] getAlias() {
        return alias;
    }

    /**
     * Set the alias list of the command.
     * @param alias the alias list of the command.
     */
    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    /**
     * Set the listener of the command.
     * @param commandListener the listener of the command.
     */
    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    /**
     * Set the description of the command.
     * @param description the description of the command.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the label of the command.
     * @param label the label of the command.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Set the example of how to use the command.
     * @param usage the example of how to use the command.
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }
}
