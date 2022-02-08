package com.chillycheesy.hometracker.commands.builder;

import com.chillycheesy.hometracker.ModuloAPI;
import com.chillycheesy.hometracker.commands.AliasManager;
import com.chillycheesy.hometracker.commands.Command;
import com.chillycheesy.hometracker.commands.CommandListener;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Builds a command from a class annotated with {@link Label}, {@link Description}, {@link Usage}.
 * The class can also implement {@link CommandListener} to receive events when the command is executed.
 */
public class CommandBuilder {

    /**
     * Builds a command from a class annotated with {@link Label}, {@link Description}, {@link Usage}.
     * @param toBuild the class to build the command from.
     * @return the built command.
     */
    public static Command build(Object toBuild) {
        final Command command = new Command();
        final Annotation[] annotations = toBuild.getClass().getDeclaredAnnotations();
        final CommandBuilder builder = new CommandBuilder();
        if (toBuild instanceof CommandListener) command.setCommandListener((CommandListener) toBuild);
        for (Annotation annotation : annotations) {
            builder.buildDecoration(command, annotation);
        }
        return command;
    }

    private void buildDecoration(Command command, Label label) {
        final String[] labels = label.value();
        final String mainLabel = labels.length > 0 ? labels[0] : "";
        final String[] aliases = labels.length > 1 ? Arrays.copyOfRange(labels, 1, labels.length) : new String[0];
        command.setLabel(mainLabel);
        if (aliases.length > 0) {
            final AliasManager aliasManager = ModuloAPI.getCommand().getMainAliasManager();
            aliasManager.registerAlias(mainLabel, aliases);
        }
    }

    private void buildDescription(Command command, Description description) {
        command.setDescription(description.value());
    }

    private void buildUsage(Command command, Usage usage) {
        command.setUsage(usage.value());
    }

    private void buildDecoration(Command command, Annotation annotation) {
        if (annotation instanceof Label) buildDecoration(command, (Label) annotation);
        else if (annotation instanceof Description) buildDescription(command, (Description) annotation);
        else if (annotation instanceof Usage) buildUsage(command, (Usage) annotation);

    }

}
