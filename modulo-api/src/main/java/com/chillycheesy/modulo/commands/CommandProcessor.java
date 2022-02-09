package com.chillycheesy.modulo.commands;

import com.chillycheesy.modulo.commands.operator.OperatorManager;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.utils.exception.CommandException;
import com.chillycheesy.modulo.utils.exception.CommandNotFoundException;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for processing commands.
 * <p>
 *     It is responsible for parsing the command and run it.
 *     It is also responsible for handling the exceptions.
 * </p>
 */
public class CommandProcessor {

    private static final String NON_COMMAND_REGEX = "(-?\\d+\\.?\\d*)|((true)|(false))";

    private CommandManager commandManager;
    private OperatorManager operatorManager;

    private final Pattern nonCommand;

    /**
     * Constructor.
     * @param commandManager The command manager.
     * @param operatorManager The operator manager.
     */
    public CommandProcessor(CommandManager commandManager, OperatorManager operatorManager) {
        this.commandManager = commandManager;
        this.operatorManager = operatorManager;
        nonCommand = Pattern.compile(NON_COMMAND_REGEX);
    }

    /**
     * Processes the command.
     * @param caller The module that called the command.
     * @param flow The command flow to process.
     * @return The command flow after processing.
     * @throws CommandException If the command fails.
     */
    public CommandFlow execute(Module caller, CommandFlow flow) throws CommandException {
        flow = operatorManager.applyOperators(caller, flow);
        final String content = flow.getContent();
        final Pattern pattern = Pattern.compile("(?<!\\\\)\"(.*?)[^\\\\]\"|\\S+");
        final Matcher matcher = pattern.matcher(content);
        return processCommands(caller, flow, matcher.results()
                .map(MatchResult::group)
                .map(group -> group.replaceAll("^\"|\"$", ""))
                .map(group -> group.replaceAll("(?<!\\\\)\\\\(?!\\\\)", "")).toArray(String[]::new));
    }

    private CommandFlow processCommands(Module caller, CommandFlow flow, String[] arguments) throws CommandException {
        if (arguments.length > 0) {
            final String label = arguments[0];
            final String[] args = arguments.length > 1 ? Arrays.copyOfRange(arguments, 1, arguments.length) : new String[0];
            final Command command = commandManager.getCommandByLabel(label);
            if (command == null)
                if (nonCommand.matcher(label).find()) return flow;
                else throw new CommandNotFoundException(flow, label);
            return command.getCommandListener().onCommand(caller, label, args, flow);
        }
        throw new CommandException(flow, 0, "No arguments provided");
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
