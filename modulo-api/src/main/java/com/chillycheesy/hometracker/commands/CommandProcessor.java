package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.modules.Module;
import com.chillycheesy.hometracker.utils.exception.CommandException;
import com.chillycheesy.hometracker.utils.exception.CommandNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandProcessor {

    private static final String NON_COMMAND_REGEX = "(-?\\d+\\.?\\d*)|((true)|(false))";

    private CommandManager commandManager;
    private OperatorManager operatorManager;

    private Pattern nonCommand;

    public CommandProcessor(CommandManager commandManager, OperatorManager operatorManager) {
        this.commandManager = commandManager;
        this.operatorManager = operatorManager;
        nonCommand = Pattern.compile(NON_COMMAND_REGEX);
    }

    public CommandFlux execute(Module caller, CommandFlux flux) throws CommandException {
        flux = operatorManager.applyOperators(caller, flux);
        final String content = flux.getContent();
        final Pattern pattern = Pattern.compile("(?<!\\\\)\"(.*?)[^\\\\]\"|\\S+");
        final Matcher matcher = pattern.matcher(content);
        return processCommands(caller, flux, matcher.results()
                .map(MatchResult::group)
                .map(group -> group.replaceAll("^\"|\"$", ""))
                .map(group -> group.replaceAll("(?<!\\\\)\\\\", "")).toArray(String[]::new));
    }

    private CommandFlux processCommands(Module caller, CommandFlux flux, String[] arguments) throws CommandException {
        if (arguments.length > 0) {
            final String label = arguments[0];
            final String[] args = arguments.length > 1 ? Arrays.copyOfRange(arguments, 1, arguments.length) : new String[0];
            final Command command = commandManager.getCommandByLabel(label);
            if (command == null)
                if (nonCommand.matcher(label).find()) return flux;
                else throw new CommandNotFoundException(flux, 0, label);
            return command.getCommandListener().onCommand(caller, label, args, flux);
        }
        throw new CommandException(flux, 0, "No arguments provided");
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
