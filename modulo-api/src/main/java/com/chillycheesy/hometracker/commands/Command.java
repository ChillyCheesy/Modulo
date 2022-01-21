package com.chillycheesy.hometracker.commands;

public class Command {

    private Module registeredModule;
    private String[] alias;
    private String description;
    private String usage;
    private CommandListener commandListener;

    public Command() { }

    public Command(Module registeredModule, String[] alias, String description, String usage, CommandListener commandListener) {
        this.registeredModule = registeredModule;
        this.alias = alias;
        this.description = description;
        this.usage = usage;
        this.commandListener = commandListener;
    }

    public Module getRegisteredModule() {
        return registeredModule;
    }

    public CommandListener getCommandListener() {
        return commandListener;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    public void setCommandListener(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegisteredModule(Module registeredModule) {
        this.registeredModule = registeredModule;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
