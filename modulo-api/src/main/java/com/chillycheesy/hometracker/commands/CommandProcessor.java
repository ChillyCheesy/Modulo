package com.chillycheesy.hometracker.commands;

import com.chillycheesy.hometracker.commands.operator.OperatorManager;
import com.chillycheesy.hometracker.modules.Module;

import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

    private CommandManager commandManager;
    private OperatorManager operatorManager;

    public CommandProcessor(CommandManager commandManager, OperatorManager operatorManager) {
        this.commandManager = commandManager;
        this.operatorManager = operatorManager;
    }

    public CommandFlux execute(Module caller, CommandFlux flux) {
        return processCommands(
                caller, operatorManager.applyOperators(caller, flux));
    }

    private CommandFlux processCommands(Module caller, CommandFlux flux) {
        final String content = flux.getContent();
        final Pattern pattern = Pattern.compile("\"([^\"]*)\"|[\\S]+");
        final Matcher matcher = pattern.matcher(content);

        /* final String[] matches = (String[]) matcher.results().map(MatchResult::group).map(group -> group.replaceAll("^\"|\"$", "")).toArray();
        if (matches.length > 0) {
            final String label = matches[0];
            final String[] args = Arrays.copyOfRange(matches, 1, matches.length);
            final Command command = commandManager.getCommandByLabel(label);
            command.getCommandListener().onCommand(caller, label, args, flux);
        }*/
        return flux;
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
